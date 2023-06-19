# Spigot Injection

### What is this?

This is a dependency injection library that includes command, configuration,
listener, scheduler and scanner system with auto-injection.
Spigot-injection uses [Guice](https://github.com/google/guice)
for dependency injection.

### How to use?

#### 1. Add dependency

#### Maven

```xml

<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.hakan-krgn.hCore</groupId>
    <artifactId>spigot-injection</artifactId>
    <version>0.0.2</version>
    <scope>compile</scope>
</dependency>
```

#### Gradle

```groovy

repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.hakan-krgn.hCore:spigot-injection:0.0.2'
}
```


#### 2. Create a main class

```java

// this package will be scanned
@Scanner("com.hakan.test")
public class MyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // this will start the injection process
        SpigotBootstrap.run(this);
    }
}
```

That's all. Now you can use injection in your classes.


#### 3. Example service

```java

@Singleton
public class MyService {

    @Inject
    public MyService() {

    }

    public void sendMessage(Player player, 
                            String message) {
        player.sendMessage(message);
        System.out.println(message);
    }
}
```


#### 4. Example command

```java

@Singleton
public class MyCommand {

    @Inject
    private MyService myService;

    @Command(
            name = "test",
            usage = "/test",
            aliases = {"test2"},
            description = "test command"
    )
    public void execute(CommandSender executor,
                        @Param Player target,
                        @Param String message,
                        @Param int amount) {
        for (int i = 0; i < amount; i++) {
            this.myService.sendMessage(target, message);
        }
    }
}
```


#### 5. Example listener

```java

@Singleton
public class MyListener {

    private final MyService myService;
    
    @Inject
    public MyListener(MyService myService) {
        this.myService = myService;
    }

    @Listener
    public void joinEvent(PlayerJoinEvent event) {
        this.myService.sendMessage(event.getPlayer(), "Welcome!");
    }
}
```


#### 6. Example scheduler

```java

@Singleton
public class MyScheduler {

    private final MyService myService;

    @Inject
    public MyScheduler(MyService myService) {
        this.myService = myService;
    }

    @Scheduler(
            delay = 1,
            period = 5,
            async = true,
            timeUnit = TimeUnit.MINUTES
    )
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.myService.sendMessage(player, "Hello!");
        }
    }
}
```


#### 7. Example configuration

THIS WILL BE UPDATED
