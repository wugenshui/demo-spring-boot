# 获取令牌

curl -X POST http://localhost:8080/api/login -H "Content-Type: application/json" ^
-d "{\"username\":\"admin\",\"password\":\"123456\"}"

# 访问授权接口

## 有权限
curl --location --request GET "http://localhost:8080/api/f1" ^
--header "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEwMCIsImV4cCI6MTc0NjU0MzQwMCwidXNlcm5hbWUiOiJhZG1pbiJ9.m-lxSqD2hw8mh6jRpXFp8rZLnRy6nMgpkivVBDNHZJU"

## 无权限
curl --location --request GET "http://localhost:8080/api/f2" ^
--header "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEwMCIsImV4cCI6MTc0NjU0MzQwMCwidXNlcm5hbWUiOiJhZG1pbiJ9.m-lxSqD2hw8mh6jRpXFp8rZLnRy6nMgpkivVBDNHZJU"
