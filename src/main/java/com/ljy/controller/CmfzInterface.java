package com.ljy.controller;

import com.ljy.dao.*;
import com.ljy.entity.*;
import com.ljy.service.AdminService;
import com.ljy.service.UserService;
import com.ljy.util.SmsUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class CmfzInterface {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CounterDao counterDao;
    @Autowired
    private GuruDao guruDao;
    @Autowired
    private BannerDao bannerDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //测试User登良
    @GetMapping(value = "/user/userLogin/{phone}")
    public User UserLogin(@PathVariable("phone") String phone) {
        return userService.queryUserByPhone(phone);
    }

    //1.user登录接口
    @GetMapping(value = "/user/login/{phone}")
    public Map login(@PathVariable("phone") String phone, String password) {
        Map map = new HashMap();
        User user = userDao.queryUserByPhone(phone);
        if (user == null) {
            //用户不存在状态为-200,提示不存在
            map.put("status", "-200");
            map.put("message", "该手机号不存在");
        } else {
            //用户存在验证密码
            if (password.equals(user.getPassword())) {
                map.put("status", "200");
                map.put("message", "登陆成功");
                map.put("user", user);
            } else {
                map.put("status", "-200");
                map.put("message", "密码错误");
            }
        }
        return map;
    }

    //2.验证码接口
    @PostMapping(value = "/user/verify/{phone}")
    public Map verify(@PathVariable("phone") String phone) {
        HashMap map = new HashMap();
        try {
            //随机生成验证码
            Random r = new Random();
            int intCode = r.nextInt(9999) + 1000;
            String strCode = String.valueOf(intCode);
            System.out.println("验证码为 = " + strCode);
            //调用阿里大于，发送验证码
            SmsUtil.send(phone, strCode);
            //把验证码存 Redis
            stringRedisTemplate.opsForValue().set(phone + "_" + strCode, strCode, 365, TimeUnit.DAYS);
            map.put("status", 200);
            map.put("message", "发送成功");
            map.put("code", strCode);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "error");
        }
        return map;

    }

    //3.注册接口
    @PostMapping(value = "/user/register/{phone}/{code}")
    public Map register(@PathVariable("phone") String phone,@PathVariable("code") String code) {
        HashMap map = new HashMap();
        try {
            //将前台发来的验证码与Redis中的验证码对比
            String redisCode = stringRedisTemplate.opsForValue().get(phone + "_" + code);
            if (code.equals(redisCode)) {
                map.put("status", 200);
                map.put("message", "验证码正确");
            } else {
                map.put("status", -200);
                map.put("message", "验证码错误");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "error");
        }
        return map;

    }

    //4.补充个人信息接口
    @PostMapping(value = "/user/insertUser")
    public Map insertUser(@RequestBody User user) {
        Map map = new HashMap();
        userService.insertUser(user);
        map.put("status", "200");
        map.put("message", "注册成功");
        map.put("user", user);
        return map;
    }

    //5. 一级页面展示接口
    @GetMapping(value = "/user/oneLevel/{id}/{type}/{sub_type}")
    public Map onePage(@PathVariable("id") String id, @PathVariable("type") String type, @PathVariable("sub_type") String sub_type) {
        HashMap hashMap = new HashMap();
        User user = userDao.selectByPrimaryKey(id);
        if (user == null) {
            hashMap.put("status", "-200");
            hashMap.put("message", "error");
        } else {
            try {
                if (type.equals("all")) {
                    List<Banner> banners = bannerDao.queryBannersByStatus("1");
                    List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status", 200);
                    hashMap.put("head", banners);
                    hashMap.put("albums", albums);
                    hashMap.put("articles", articles);
                } else if (type.equals("wen")) {
                    List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                    hashMap.put("status", 200);
                    hashMap.put("albums", albums);
                } else {
                    if (sub_type.equals("ssyj")) {
                        List<Article> articles = articleDao.selectAll();
                        hashMap.put("status", 200);
                        hashMap.put("articles", articles);
                    } else {
                        List<Article> articles = articleDao.selectAll();
                        hashMap.put("status", 200);
                        hashMap.put("articles", articles);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                hashMap.put("status", "-200");
                hashMap.put("message", "error");
            }

        }
        return hashMap;
    }


    //6. 文章详情接口
    @GetMapping(value = "/article/queryArticle/{id}")
    public Map queryArticle(@PathVariable("id") String id) {
        Map map = new HashMap();
        Article article = articleDao.selectByPrimaryKey(id);
        if (article == null) {
            map.put("status", "-200");
            map.put("message", "没有此文章");
        } else {
            map.put("status", "200");
            map.put("message", "查看文章详情成功");
            map.put("article", article);
        }
        return map;
    }

    //7. 专辑详情接口
    @GetMapping(value = "/album/queryOneAlbum/{id}")
    public Map queryOneAlbum(@PathVariable("id") String id) {
        Map map = new HashMap();
        Album album = albumDao.selectByPrimaryKey(id);
        if (album == null) {
            map.put("status", "-200");
            map.put("message", "没有此专辑");
        } else {
            map.put("status", "200");
            map.put("message", "查看专辑详情成功");
            map.put("album", album);
        }
        return map;
    }

    //8. 展示功课
    @GetMapping(value = "/course/queryOneCourse/{id}")
    public Map queryOneCourse(@PathVariable("id") String id) {
        Map map = new HashMap();
        List<Course> courseList = courseDao.queryCourseByUserId(id);
        if (courseList == null) {
            map.put("status", "-200");
            map.put("message", "没有此用户");
        } else {
            map.put("status", "200");
            map.put("message", "查看专辑详情成功");
            map.put("courseList", courseList);
        }
        return map;
    }

    //9. 添加功课
    @PostMapping(value = "/course/insertCourse/{id}")
    public Map insertCourse(@PathVariable("id") String id, @RequestBody Course course) {
        Map map = new HashMap();
        //查询用户是否存在
        User user = userDao.selectByPrimaryKey(id);
        if (user == null) {
            map.put("status", "-200");
            map.put("message", "该用户不存在,无法添加功课");
        } else {
            //存在,获取userId，添加到该用户下的功课
            String courseId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
            course.setId(courseId);
            course.setUserId(user.getId());
            courseDao.insert(course);
            map.put("status", "200");
            map.put("message", "添加功课成功");
            map.put("course", course);
        }
        return map;
    }

    //10. 删除功课
    @DeleteMapping(value = "/course/deleteCourse/{id}/{courseId}")
    public Map deleteCourse(@PathVariable("id") String id, @PathVariable("courseId") String courseId) {
        Map map = new HashMap();
        User user = userDao.selectByPrimaryKey(id);
        if (user == null) {
            map.put("status", "-200");
            map.put("message", "该用户不存在,无法删除功课");
        } else {
            //用户存在则根据功课ID查当前功课
            Course course = courseDao.queryCourseById(courseId);
            String id1 = course.getId();
            courseDao.deleteByPrimaryKey(id1);
            map.put("status", "200");
            map.put("message", "删除功课成功");
        }
        return map;
    }

    //11. 展示计数器
    @GetMapping(value = "/counter/queryOneCounter/{userId}/{courseId}")
    public Map queryOneCounter(@PathVariable("userId") String id, @PathVariable("courseId") String courseId) {
        Map map = new HashMap();
        Counter counter = new Counter();
        User user = userDao.selectByPrimaryKey(id);
        if (user == null) {
            map.put("status", "-200");
            map.put("message", "该用户不存在");
        } else {
            //用户存在判断功课是否存在
            Course course = courseDao.queryCourseById(courseId);
            if (course == null) {
                map.put("status", "-200");
                map.put("message", "功课不存在");
            } else {
                //功课存在
                counter.setUserId(user.getId());
                counter.setCourseId(course.getId());
                Counter counter1 = counterDao.selectOne(counter);
                if (counter1 != null) {
                    map.put("status", "200");
                    map.put("message", "展示成功");
                    map.put("counter1", counter1);
                } else {
                    map.put("status", "-200");
                    map.put("message", "计数器不存在");
                }
            }

        }
        return map;
    }

    //12. 添加计数器
    @PostMapping(value = "/counter/insertCounter/{id}/{courseId}")
    public Map insertCounter(@PathVariable("id") String id, @PathVariable("courseId") String courseId, @RequestBody Counter counter) {
        Map map = new HashMap();
        //判断用户是否存在
        User user = userDao.selectByPrimaryKey(id);
        if (user == null) {
            map.put("status", "-200");
            map.put("message", "用户不存在");
        } else {
            //判断功课是否存在
            Course course = courseDao.queryCourseById(courseId);
            if (course == null) {
                map.put("status", "-200");
                map.put("message", "功课不存在");
            } else {
                String counterId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
                counter.setId(counterId);
                counter.setUserId(user.getId());
                counter.setCourseId(course.getId());
                counterDao.insert(counter);
                map.put("status", "200");
                map.put("message", "添加计数器成功");
                map.put("counter", counter);
            }
        }
        return map;
    }

    //13. 删除计数器
    @DeleteMapping(value = "/counter/deleteCounter/{id}/{courseId}/{counterId}")
    public Map deleteCounter(@PathVariable("id") String id, @PathVariable("courseId") String courseId, @PathVariable("counterId") String counterId) {
        Map map = new HashMap();
        User user = userDao.selectByPrimaryKey(id);
        if (user == null) {
            map.put("status", "-200");
            map.put("message", "用户不存在");
        } else {
            //判断功课是否存在
            Course course = courseDao.queryCourseById(courseId);
            if (course == null) {
                map.put("status", "-200");
                map.put("message", "功课不存在");
            } else {
                //用户功课都存在则删除当前计数器
                Counter counter = counterDao.queryCounterById(counterId);
                counterDao.deleteByPrimaryKey(counter.getId());
                map.put("status", "200");
                map.put("message", "删除计数器成功");
            }
        }
        return map;
    }

    //14. 表更计数器
    @PutMapping(value = "/counter/updateCounter/{id}/{courseId}/{counterId}")
    public Map updateCounter(@PathVariable("id") String id, @PathVariable("courseId") String courseId, @PathVariable("counterId") String counterId, @RequestBody Counter counter) {
        Map map = new HashMap();
        User user = userDao.selectByPrimaryKey(id);
        if (user == null) {
            map.put("status", "-200");
            map.put("message", "用户不存在");
        } else {
            //判断功课是否存在
            Course course = courseDao.queryCourseById(courseId);
            if (course == null) {
                map.put("status", "-200");
                map.put("message", "功课不存在");
            } else {
                Counter counter1 = counterDao.queryCounterById(counterId);
                if (counter1 != null) {
                    counter.setId(counter1.getId());
                    counter.setUserId(user.getId());
                    counter.setCourseId(course.getId());
                    counterDao.updateByPrimaryKeySelective(counter);
                    map.put("status", "200");
                    map.put("message", "修改计数器成功");
                    map.put("counter", counter);
                } else {
                    map.put("status", "-200");
                    map.put("message", "修改计数器失败");
                }

            }
        }
        return map;
    }

    //15. 修改个人信息
    @PutMapping(value = "/user/updateUser")
    public Map updateUser(@RequestBody User user) {
        Map map = new HashMap();
        userDao.updateByPrimaryKeySelective(user);
        map.put("status", "200");
        map.put("message", "修改用户信息成功");
        return map;
    }

    //16. 金刚道友
    @GetMapping(value = "/user/queryFiveUser")
    public Map queryFiveUser() {
        Map map = new HashMap();
        List<User> userList = userDao.queryFiveUser();
        map.put("status", "200");
        map.put("message", "获取5条用户信息成功");
        map.put("userList", userList);
        return map;
    }

    //17. 展示上师列表
    @GetMapping(value = "/guru/queryAllGuru")
    public Map queryAllGuru() {
        Map map = new HashMap();
        List<Guru> guruList = guruDao.selectAll();
        map.put("status", "200");
        map.put("message", "展示成功");
        map.put("guruList", guruList);
        return map;
    }

    //18. 添加关注上师
    @PostMapping(value = "/guru/insertGuru")
    public Map insertGuru(@RequestBody Guru guru) {
        Map map = new HashMap();
        String guruId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        guru.setId(guruId);
        guruDao.insert(guru);
        map.put("status", "200");
        map.put("message", "添加成功");
        map.put("guru", guru);
        return map;
    }

}