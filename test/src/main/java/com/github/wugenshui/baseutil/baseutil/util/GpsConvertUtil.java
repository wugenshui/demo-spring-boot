package com.github.wugenshui.baseutil.baseutil.util;

/**
 * GPS坐标系转换帮助类
 *
 * <p>
 * WGS84坐标系：大地坐标系，GPS原始坐标系     目前广泛使用的GPS全球卫星定位系统使用的坐标系（国外）；Google地球<br />
 * GCJ02坐标系：国测局坐标系，火星坐标系      由中国国家测绘局制定的地理坐标系统，是由WGS84加密后得到的坐标系；高德、腾讯、Google中国<br />
 * BD09坐标系：百度地图采用的坐标系           在GCJ02坐标系基础上再次加密<br />
 * longitude    经度(国内范围73.33-135.05)<br />
 * latitude     纬度(国内范围3.51-53.33)<br />
 * </p>
 *
 * @author : chenbo
 * @date : 2020-03-25
 */
public class GpsConvertUtil {
    private GpsConvertUtil() {
    }

    /**
     * 圆周率
     */
    private static final double PI = 3.1415926535897932384626;
    private static final double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
    /**
     * 克拉索夫斯基椭球参数长半轴 a
     */
    private static final double A = 6378245.0;

    /**
     * 克拉索夫斯基椭球参数第一偏心率平方
     */
    private static final double EE = 0.00669342162296594323;

    /**
     * 中国经度下限
     */
    private static final double CHINA_LNG_MIN = 72.004;
    /**
     * 中国经度上限
     */
    private static final double CHINA_LNG_MAX = 137.8347;
    /**
     * 中国纬度下限
     */
    private static final double CHINA_LAT_MIN = 0.8293;
    /**
     * 中国纬度上限
     */
    private static final double CHINA_LAT_MAX = 55.8271;

    /**
     * WGS84(大地坐标系) 转 GCJ02(火星坐标系)
     *
     * @param lng 纬度
     * @param lat 经度
     * @return 经纬度数组 [纬度，经度]
     */
    public static double[] wgs84ToGcj02(double lat, double lng) {
        if (outOfChina(lat, lng)) {
            return new double[]{lat, lng};
        }
        double dLat = transformLat(lng - 105.0, lat - 35.0);
        double dLng = transformLng(lng - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * PI);
        dLng = (dLng * 180.0) / (A / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lng + dLng;
        return new double[]{mgLat, mgLon};
    }

    /**
     * GCJ02(火星坐标系) 转 WGS84(大地坐标系)
     *
     * @param gcjLat GCJ02纬度
     * @param gcjLng GCJ02经度
     * @return 经纬度数组 [纬度，经度]
     */
    public static double[] gcj02ToWgs84(double gcjLat, double gcjLng) {
        double[] gps = wgs84ToGcj02(gcjLat, gcjLng);
        double lontitude = gcjLng * 2 - gps[1];
        double latitude = gcjLat * 2 - gps[0];
        return new double[]{latitude, lontitude};
    }

    /**
     * GCJ02(火星坐标系) 转 BD09(百度坐标系)
     *
     * @param gcjLat GCJ02纬度
     * @param gcjLng GCJ02经度
     * @return 经纬度数组 [纬度，经度]
     */
    public static double[] gcj02ToBd09(double gcjLat, double gcjLng) {
        double z = Math.sqrt(gcjLng * gcjLng + gcjLat * gcjLat) + 0.00002 * Math.sin(gcjLat * X_PI);
        double theta = Math.atan2(gcjLat, gcjLng) + 0.000003 * Math.cos(gcjLng * X_PI);
        double tempLng = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new double[]{tempLat, tempLng};
    }

    /**
     * BD09(百度坐标系) 转 GCJ02(火星坐标系)
     *
     * @param bdLat BD09纬度
     * @param bdLng BD09经度
     * @return 经纬度数组 [纬度，经度]
     */
    public static double[] bd09ToGcj02(double bdLat, double bdLng) {
        double x = bdLng - 0.0065;
        double y = bdLat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double tempLng = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        return new double[]{tempLat, tempLng};
    }

    /**
     * WGS84(大地坐标系) 转 BD09(百度坐标系)
     *
     * @param lat 纬度
     * @param lng 经度
     * @return 经纬度数组 [纬度，经度]
     */
    public static double[] wgs84ToBd09(double lat, double lng) {
        double[] gcj02 = wgs84ToGcj02(lat, lng);
        return gcj02ToBd09(gcj02[0], gcj02[1]);
    }

    /**
     * BD09(百度坐标系) 转 WGS84(大地坐标系)
     *
     * @param bdLat BD09纬度
     * @param bdLng BD09经度
     * @return 经纬度数组 [纬度，经度]
     */
    public static double[] bd09ToWgs84(double bdLat, double bdLng) {
        double[] gcj02 = bd09ToGcj02(bdLat, bdLng);
        double[] gps84 = gcj02ToWgs84(gcj02[0], gcj02[1]);
        // 保留小数点后六位
        gps84[0] = retain6(gps84[0]);
        gps84[1] = retain6(gps84[1]);
        return gps84;
    }

    /**
     * 经纬度转墨卡托投影坐标系
     *
     * @param lng 经度
     * @param lat 纬度
     * @return 经纬度数组 [墨卡托坐标x，墨卡托坐标y]
     */
    public static double[] lngLatToMercator(double lng, double lat) {
        double mercatorX = lng * 20037508.342789 / 180;
        double mercatorY = Math.log(Math.tan((90 + lat) * PI / 360)) / (PI / 180);
        mercatorY = mercatorY * 20037508.34789 / 180;
        return new double[]{mercatorX, mercatorY};
    }

    /**
     * 墨卡托投影坐标系转经纬度
     *
     * @param mercatorX 墨卡托坐标x
     * @param mercatorY 墨卡托坐标y
     * @return 经纬度数组 [经度，纬度]
     */
    public static double[] mercatorToLngLat(double mercatorX, double mercatorY) {
        double lng = mercatorX / 20037508.342789 * 180;
        double lat = mercatorY / 20037508.342789 * 180;
        lat = 180 / PI * (2 * Math.atan(Math.exp(lat * PI / 180)) - PI / 2);
        return new double[]{lng, lat};
    }

    /**
     * 获取两个经纬度的距离,单位：米
     *
     * @param latitude1  纬度1
     * @param longitude1 经度1
     * @param latitude2  纬度2
     * @param longitude2 经度2
     * @return 距离，单位：米
     */
    public static double getDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double lat1 = (Math.PI / 180) * latitude1;
        double lng1 = (Math.PI / 180) * longitude1;
        double lat2 = (Math.PI / 180) * latitude2;
        double lng2 = (Math.PI / 180) * longitude2;
        // 地球半径
        double r = 6371;
        // 两点间距离 km
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1)) * r;
        // 返回单位米
        return d * 1000;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLng(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 坐标是否超出中国
     *
     * @param lat 纬度
     * @param lng 经度
     * @return
     */
    public static boolean outOfChina(double lat, double lng) {
        // 若经纬度在中国经纬度范围之外则为国外坐标
        return lng < CHINA_LNG_MIN || lng > CHINA_LNG_MAX || lat < CHINA_LAT_MIN || lat > CHINA_LAT_MAX;
    }

    /**
     * 保留小数点后六位
     *
     * @param num 数字
     * @return 转换后的数值
     */
    private static double retain6(double num) {
        String result = String.format("%.6f", num);
        return Double.valueOf(result);
    }
}
