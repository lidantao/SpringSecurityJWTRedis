# 使用 SpringSecurity + Redis + JWT 构建安全 认证+授权 中心

### SpringSecurity-2.5.0版本，目前具有15个过滤器。其中具有三个核心的过滤器，分别是：
1. 认证过滤器：UsernamePasswordAuthenticationFilter
2. 授权过滤器：FiterSecurityInterceptor
3. 异常过滤器：ExceptionTranslationFilter


### 核心逻辑
#### 1. 认证逻辑（✔）
##### 登录功能
- 第一次访问：
    - 客户端携带用户名、密码，服务端进行校验；
    - 服务端校验成功，生成token，返回给客户端，同时将用户id作为key，用户信息作为value存储到Redis；
    - 服务端校验成功，SpringSecurity会将认证成功的用户信息存储到SecurityContextHolder（用户信息：用户名、密码、权限信息）；
- 第二次访问：
    - 客户端在请求头携带token，服务端进行校验；
##### 退出功能
- 客户端携带token，访问退出接口，服务端校验token有效性，然后根据userId清除以下两个存储：
    - Redis用户信息
    - SecurityContextHolder用户信息
   
#### 2. 授权逻辑（✔）
- 当用户认证通过之后，访问菜单接口需要进行鉴权操作。因此在认证通过之后，将用户的权限信息存储在SecurityContextHolder，等待鉴权时校验。


#### 3. 其他鉴权（✔）
- 为了防止第三方截取token进行请求，对认证通过的用户ip地址保存至token，与下次携带token访问系统的用户进行匹配。
