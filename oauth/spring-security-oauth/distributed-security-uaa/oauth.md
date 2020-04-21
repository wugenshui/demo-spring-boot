# authorization_code 授权码模式
### 获取code 
http://localhost:8080/oauth/authorize?client_id=client&response_type=code

返回回调地址加上code
 
###  获取token
post http://client:secret@localhost:8080/oauth/token

grant_type:authorization_code
code:rv6z6k



# 客户端模式

### get http://client:secret@localhost:8080/oauth/token?grant_type=client_credentials&scope=all



# 通用接口

### 校验令牌
http://localhost:8080/oauth/check_token?token=8cafa4c4-3069-4571-87d9-e452ce794cde