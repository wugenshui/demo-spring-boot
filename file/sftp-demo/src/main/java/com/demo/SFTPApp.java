package com.demo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author : chenbo
 * @date : 2026-01-21
 */
public class SFTPApp {
    public static void main(String[] args) {
        String host = "192.168.3.25";
        int port = 2222;
        String username = "admin";
        String password = "admin"; // 或使用私钥认证

        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp sftpChannel = null;

        try {
            // 创建会话
            session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // 跳过主机密钥检查（仅用于测试！生产环境应验证主机密钥）
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // 连接
            session.connect();
            System.out.println("Connected to SFTP server.");

            // 打开 SFTP 通道
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;

            // 列出目录内容
            System.out.println("ls /");
            sftpChannel.ls("/").forEach(System.out::println);

            System.out.println("ls /test");
            sftpChannel.ls("/test").forEach(System.out::println);

            // 示例：上传文件
            String localFilePath = "D:\\WorkSpace\\demo-spring-boot\\file\\sftp-demo\\src\\main\\java\\com\\demo\\SFTPApp.java";
            String remoteFilePath = "/test/file.txt";
            sftpChannel.put(new FileInputStream(localFilePath), remoteFilePath);
            System.out.println("File uploaded.");

            // 示例：下载文件
            String downloadRemotePath = "/test/file.txt";
            String localDownloadPath = localFilePath + ".bak";
            sftpChannel.get(downloadRemotePath, new FileOutputStream(localDownloadPath));
            System.out.println("File downloaded.");

        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
        } finally {
            if (sftpChannel != null && sftpChannel.isConnected()) {
                sftpChannel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
