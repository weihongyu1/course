# SQL注入攻击演示系统

## 项目介绍

### **问题描述：**

设计并实现一个适合新手用户学习的攻击演示系统。通过该系统，新手用户可以了解到当前常见攻击发生的步骤、原理和防御方法。

**要求：**

基于B/S结构，完成如下功能：

（1）   界面方面：可配置、可显示系统运行结果。

（2）   功能设计：

l 包括攻击原理介绍、攻击演示、攻击代码分析、防御方法介绍与展示等模块。

l 便于用户观看的攻击演示效果，易于操作。

（3）   代码部分：程序源程序要有相应注释，每个函数要写明作用、入口参数含义、返回值含义，程序代码的关键部分添加注释。

### 项目地址

**gitee地址：**https://gitee.com/vvwhyyy/couse

**github地址：**https://github.com/weihongyu1/course

## 方案设计

### 系统设计

  系统采用B/S架构，使用MVC业务逻辑基本结构。

​                                        ![image-20210731161326996](https://img2020.cnblogs.com/blog/2211066/202107/2211066-20210731161332598-2054807603.png)   

### 模块设计

###### 密码验证模块

在网站登陆时，密码一般采用加盐加密的形式来验证。其采用如下的一般过程：

  **注册阶段**

1. 用户输入用户名和密码等相关信息，提交给服务端

2. 服务端产生一个随机字符串，称为盐值

3. 加盐：将盐值加入到用户密码末尾

4. 加密：使用对称密码算法，如SM4，AES，3DES等密码算法对其加密

5. 哈希：使用hash算法（SM3，MD5等）对加密后的字符串进行hash，产生新密码

6. 保存：将新密码和盐值保存到数据库中

7. 完成注册阶段

**登录阶段**

1. 用户输入用户名、密码提交给服务端

2. 服务端根据用户的用户名查询字段，查询不到返回

3. 将用户密码+查询到的盐值进行合并

4. 使用注册阶段对此字符串加盐加密

5. 登录时提交处理后的字符串和查询到的密码比较

6. 相等登录成功，否则返回

在此过程中使用对称加密保证了数据的机密性，哈希算法保证了数据的完整性，保证黑客即使拿到数据库中的数据也无法恢复出用户的密码信息。

###### Cookie+Session登录认证模块

  HTTP 是一种无状态的协议，客户端每次发送请求时，首先要和服务器端建立一个连接，在请求完成后又会断开这个连接。这种方式可以节省传输时占用的连接资源，但同时也存在一个问题：每次请求都是独立的，服务器端无法判断本次请求和上一次请求是否来自同一个用户，进而也就无法判断用户的登录状态。

  Cookie+Session的登陆机制就是为了解决这个问题。

  **用户第一次登录**

​                                                  ![image-20210731161348952](https://img2020.cnblogs.com/blog/2211066/202107/2211066-20210731161349569-2054780013.png)

1. 用户访问a.com/PageA，并输入登录

2. 服务器使用上述密码验证成功后，会创建SessionId 

3. 设置Cookie，将SessionId写入Cookie中

**后续访问**

  第一次登录完成后，后续的访问就可以直接使用Cookie进行身份验证

​                                              ![image-20210731161406456](https://img2020.cnblogs.com/blog/2211066/202107/2211066-20210731161407104-1849886447.png)

1. 用户访问其它页面a.com/PageB，将设置好的Cookie传递个服务端

2. 服务端验证Cookie是否和Session中SessionId相同

3. 不相同，返回登录

4. 相同登录成功

###### 课程介绍及学习模块

​    此模块旨在打造用户体验感极强的课程选取和学习交互系统。

1. 登录验证

2. 查看课程介绍界面

3. 选取学习的内容进入学习界面

4. 学习界面登录验证

5. 进入学习界面学习内容

![image-20210731161422653](https://img2020.cnblogs.com/blog/2211066/202107/2211066-20210731161423295-1456110319.png)

## 系统实现

### 接口文档

用户登录接口

**用户登录接口**

```java
/**

  \* 用户登录接口

   \* 登录成功返回课程界面

   */

  @RequestMapping("/login")

  public String login(HttpServletRequest request){

​    String id = request.getParameter("userId");

​    String pwd = request.getParameter("pwd");

​    HttpSession session = request.getSession();

​    session.setAttribute("login",id);

​    User user = new User(Integer.valueOf(id), pwd, null);

​    boolean login = userService.login(user);

​    if (login){

​      return "study/courses";

​    }

​    return "user/signIn";

  }
```

**用户注册接口**

```java
/**

  \* 用户注册接口

   \* 注册成功返回登录界面

   */

  @RequestMapping("/register")

  public String register(HttpServletRequest request){

​    String id = request.getParameter("username");

​    String pwd = request.getParameter("pwd");

​    User user = new User(Integer.valueOf(id), pwd, null);

​    boolean register = userService.register(user);

​    if (register){

​      return "user/signIn";

​    }

​    return "user/register";

  }
```

**请求课程页面接口**

```java
/**

  \* 请求课程页面接口

   \* @return 返回课程页面

   */

  @RequestMapping("/courses")

  public String courses(HttpServletRequest request, Model model){

​    Object login = request.getSession().getAttribute("login");

​    model.addAttribute("login",login);

​    if (login == null){

​      return "user/signIn";

​    }

​    return "study/courses";

  }
```

**请求学习页面接口**

```java
/**

  \* 请求学习页面接口

   */

  @RequestMapping("/sqlInjection")

  public String sqlInjection(HttpServletRequest request,Model model){

​    Object login = request.getSession().getAttribute("login");

​    if (login == null){

​      return "user/signIn";

​    }

​    model.addAttribute("login",login);

​    return "study/sqlInjectionStep/sqlInjection";

  }
```



### 核心代码设计

SM3 hash

```java
/**

   \* 对字符串进行hash

   \* @param str 需要hash的明文

   \* @return 返回hash后字符串

   */

  public static String hashStr(String str){

​    BouncyCastleProvider provider = new BouncyCastleProvider();

​    try {

​      MessageDigest digest = MessageDigest.getInstance("SM3",provider);

​      byte[] encode = Hex.encode(digest.digest(str.getBytes(StandardCharsets.UTF_8)));

​      String s = new String(encode);

​      return s;

​    } catch (NoSuchAlgorithmException e) {

​      e.printStackTrace();

​    }

​    return new String();

  }
```

SM4加密

```java
/**

   \* 加密，SM4-ECB-PKCS5Padding

   *

   \* @param data 要加密的明文

   \* @param key 密钥16字节，使用Sm4Util.generateKey()生成

   \* @return 加密后的密文

   \* @throws Exception 加密异常

   */

  public static byte[] encryptEcbPkcs5Padding(byte[] data, byte[] key) throws Exception {

​    return sm4(data, key, ALGORITHM_ECB_PKCS5PADDING, null, Cipher.ENCRYPT_MODE);

  }
```

JDBC连接数据库设计

```java
/**

   \* 获取数据库连接

   \* @return

   \* @throws Exception

   */

  public static Connection getConnection() throws Exception {

 

​    String url = "jdbc:mysql://127.0.0.1:3306/qianyu_study";

​    String driver = "com.mysql.cj.jdbc.Driver";

 

​    //加载dirver

​    Class.forName(driver);

 

​    //获取连接

​    Connection conn = DriverManager.getConnection(url,"root","root");

 

​    return conn;

  }

 

  /**

  \* 关闭连接

   */

  public void closeResource(Connection conn, Statement ps){

​    try {

​      if (conn!=null){

​        conn.close();

​      }

​      if (ps!=null){

​        ps.close();

​      }

​    } catch (SQLException throwables) {

​      throwables.printStackTrace();

​    }

  }

 

  /**

   \* 通用查询

   \* @param sql SQL语句

   \* @param args sql参数

   \* @return 返回查询结构

   \* @throws Exception

   */

  public static Test01 queryForUser(String sql,Object ...args) throws Exception {

​    Connection conn = null;

​    PreparedStatement ps = null;

​    ResultSet rs = null;

​    Object bj = new Object();

​    try {

​      conn = getConnection();

 

​      ps = conn.prepareStatement(sql);

​      for (int i = 0; i < args.length ; i++) {

​        ps.setObject(i+1,args[i]);

​      }

 

​      rs = ps.executeQuery();

​      //获取结果集的元数据

​      ResultSetMetaData rsmd = rs.getMetaData();

​      //获取结果集列数,查的字段数

​      int columnCount = rsmd.getColumnCount();

​      if (rs.next()){

​        Test01 test01 = new Test01();

​        //处理结果集一行数据中的字段

​        for (int i = 0; i < columnCount ; i++) {

 

​          //获取字段值，通过结果集

​          Object value = rs.getObject(i + 1);

 

​          //获取结果集中的字段名，通过元数据

​          //获取列的别名

​          String columnName = rsmd.getColumnLabel(i+1);

 

​          //给user对象指定的指定属性赋值为value,通过反射

​          //**注意属性名和字段名不同

​          Field field = Test01.class.getDeclaredField(columnName);

​          field.setAccessible(true);

​          field.set(test01,value);

 

​        }

​        return test01;

​      }

​    } catch (Exception e) {

​      e.printStackTrace();

​    } finally {

​      closeResource(conn,ps,rs);

​    }

​    return null;

  }
```

密码加盐加密处理

```java
/**

   \* 加盐加密

   \* @param psd 密码明文

   \* @return 返回加盐加密后的新密码

   */

  public static String merge(String psd,String salt) {

​    //加盐

​    String str = Sm3Util.addSalt(psd, salt);

​    //Sm4加密

​    byte[] key = Sm4Util.getBytes("whylovewyy", 16);

​    try {

​      byte[] bytes = Sm4Util.encryptEcbPkcs5Padding(str.getBytes(),key);

​      //hash

​      String pwdNew = Sm3Util.hashStr(new String(bytes));

​      return pwdNew;

​    } catch (Exception e) {

​      e.printStackTrace();

​    }

​    return null;

  }

用户登录、注册具体设计

 /**

  \* 登录设计

   \* @param user 用户信息

   */

  @Override

  public boolean login(User user) {

​    User user1 = null;

​    if (user.getId() != null){

​      user1 = userMapper.getUser(user.getId());

​    }

​    //加盐加密

​    String merge = SaltUtil.merge(user.getPwd(), user1.getSalt());

​    System.out.println(merge);

​    if (merge.equals(user1.getPwd())){

​      return true;

​    }

 

​    return false;

  }

 

  /**

  \* 注册设计

   \* @param user 用户注册信息

   */

  @Override

  public boolean register(User user) {

​    String salt = Sm3Util.creatSalt();

​    String merge = SaltUtil.merge(user.getPwd(), salt);

​    user.setPwd(merge);

​    user.setSalt(salt);

​    userMapper.insertUser(user);

​    return true;

  }
```

## 成果展示

实现了SQL注入攻击演示系统

**主页**

​                                                  ![image-20210731161447869](https://img2020.cnblogs.com/blog/2211066/202107/2211066-20210731161448816-1279834316.png)

**登录、注册**

​                                                  ![image-20210731161455547](https://img2020.cnblogs.com/blog/2211066/202107/2211066-20210731161456116-566923838.png)

![image-20210731161513026](https://img2020.cnblogs.com/blog/2211066/202107/2211066-20210731161513783-914487209.png)

**课程界面**

​                                                  ![image-20210731161613123](https://img2020.cnblogs.com/blog/2211066/202107/2211066-20210731161613762-1339146985.png)

**学习界面**

​                                                   ![image-20210731161632881](https://img2020.cnblogs.com/blog/2211066/202107/2211066-20210731161633545-198203211.png)                 

**注入靶机**

​                                                          ![image-20210731161654331](https://img2020.cnblogs.com/blog/2211066/202107/2211066-20210731161654921-1863098241.png)

