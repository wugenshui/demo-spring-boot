# 授权码模式 authorization_code
### 获取code 
http://localhost:8000/oauth/authorize?client_id=client&response_type=code

返回回调地址加上code
 
###  获取token
post http://client:secret@localhost:8000/oauth/token

grant_type:authorization_code  
code:rv6z6k  



# 隐式授权模式 implicit
### 回调地址上显示token
get http://localhost:8000/oauth/authorize?response_type=token&client_id=client
 


# 客户端模式 client_credentials

### get http://client:secret@localhost:8000/oauth/token?grant_type=client_credentials



# 密码模式 password

### post http://client:secret@localhost:8000/oauth/token
username:admin  
password:admin  
grant_type:password  
scope:all  



# 通用接口

### 校验令牌
http://localhost:8000/oauth/check_token?token=8cafa4c4-3069-4571-87d9-e452ce794cde

### 刷新令牌
POST http://localhost:8000/oauth/token

grant_type:refresh_token
refresh_token:我的token
client_id:client
client_secret:secret