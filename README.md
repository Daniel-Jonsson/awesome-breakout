# Final Project

## 1 Environment & Tools
The following IDE and tools have been used during the implementation
for solving the problems in the given project:

* git version 2.37.2.windows.2


* openjdk 19.0.2 2023-01-17


* Apache Maven 3.9.1


* IntelliJ IDEA 2022.3.3 (Ultimate Edition)


* JUnit (for testing purposes)

These environments and tools are used to streamline the process while also preventing possible loss of data with the use of version control.


## 2 Purpose
The purpose of this project is to showcase knowledge relevant to the curriculums scope. Demonstrating the knowledge 
on event-driven application development using the Java Standard Library `javax.swing` while doing so under the 
MVC-architecture. To fulfill the purpose, a GUI application was needed to be developed. This specific project was 
created to implement the requirements needed for grade "B". The requirements needed to achieve this grade are as 
follows:

* Developing an Event Driven Application using GUI
* A minimum amount of five concurrent processes
* At least three processes accessing a shared resource in a threadsafe way
* A minimum amount of six unique Swing components
* At least four unique Swing layouts
* Six design patterns needed to be applied correctly
* At least three unique Streams API utilizations
* An application built using MVC architecture

The following sections will provide information on how these requirements were achieved

## 3 Procedures

### 3.1 Overall design thinking
When building an application of this size, even if it is not a huge application, will require some 
Design Thinking utilization in order to get a solid idea on how the application is intended to work. The project 
description provided a list of requirements as seen in the purpose section above, the design of the application was 
thoroughly analysed in order to implement as many of the requirements as possible without the need to force any of 
them in the application in order to fulfill a requirement. By doing this, a game which is similar to the famous 
**Breakout** game was the application idea that fulfilled the most requirements in a natural way but still involved 
game-development.

After determining the suitable game to develop, a skeleton structure was created with pen and paper in order to get 
a full understanding on exactly where each requirement would fit the most. The structure created was later 
implemented as a skeleton code base in IntelliJ and later implemented with code.

### 3.2 Implementation Process

In this section the implementation process will be looked at and later discussed in the discussion section below.

**Models**

When creating an application using the MVC architecture, the most common approach is to begin with implementing the 
model classes. Since these classes are supposed to contain the data and logic for driving this application it is the 
most reasonable to begin developing. By doing so more ideas might appear which would make it easy to apply to the 
Controllers and Views classes without having to restructure their logic. The packages inside the model in this 
application is as follows:

* ball
* bricks
* paddle
* powerup


**ball package**

The ball package contains classes about different concrete balls used in the game. The `BallModel` class is the base 
class for the balls that is subclassed into three concrete types of balls (EasyBallModel, MediumBallModel, 
HardBallModel). Since there are no obvious difference between the ball in behaviour most of the logic for each ball 
resides inside their superclass. However, for later development each ball might have different behaviour. The hard 
ball might be implemented with special physics like for example spinning/curving when hitting certain things. There 
is an Interface(BallFactory) that provides a contract for how concrete factories should create ball instances in the 
game. This is a functional interface with a single abstract method `createBall`. The interface is implemented by an 
abstract ball factory class which serves as a convenient class for concrete factories in order to reduce code 
duplication. Since all concrete factories should pass a `BreakoutModel` instance when creating their specific ball 
types, utilizing the convenient factory `AbstractBallFactory` and using its `BreakoutModel` instance variable there 
is no need for each concrete factory to have it´s own instance variable which reduces code duplication and 
encapsulates the instance variable to the `AbstractBallFactory`.

Each of the concrete factory creates specific concrete Balls, *EasyBallModel*, *MediumBallModel*, and 
*HardBallModel* which is used in different situations. The concrete balls are subclasses of the abstract class 
**BallModel** which contains most of the logic of each concrete ball. Since the balls use the same logic in regard 
to movement and such, encapsulating this logic to an more general `BallModel` is a good approach in order to reduce 
code duplication and follows the DRY(don't repeat yourself) principle. The difference between each concrete ball is 
their size and speed. However, one method that is declared abstract in the `BallModel` is the `updateEffect` used 
when a ball has a powerup effect applied to it, this is needed to be implemented in each concrete ballmodel class in 
order to properly reset its size to the correct original size when the effect should run out.

Overall, these classes fulfills a requirement in the purpose, namely **Factory Method Pattern**. It defines an 
interface for creating objects of different concrete balls. It offers a single abstract method for creating a ball 
and each concrete factory creates specific instances of concrete balls. 

**bricks package**
The bricks package contains two classes, a single `Brick` and a class representing an array of bricks in a field 
`BrickField`. These contain logic specific to bricks in the game, the `BrickField` class is instantiated inside the 
`BreakoutModel` constructor and what it ultimately does is collects constant-information from the utility class 
`Appconfig` and calls the `initBricks()` method to create an array of bricks to display. This class also has methods 
for getting a certain brick at an index, checking if the brick collides with a ball and a way to remove a certain 
brick upon being hit by a ball. These classes fulfill no direct requirements but are vital for the game to work as 
intended.

**paddle package**

The paddle package contains information for various concrete paddle factories/products. It works very similar to the 
balls in the game where there is a functional interface `PaddleFactory` which defines a single abstract method 
`createPaddle()` used by concrete factories. However, there is a slight difference in the structure of ball/paddle 
packages. The paddle package does not contain the convenient class `AbstractPaddleFactory` which derivatives can 
utilize in order to follow the DRY principle. As can be seen, there is an unnecessary amount of repetition inside 
the concrete factories where each factory handles their own `BreakoutModel` instead of letting a more general 
factory handle this instance. This was chosen to be kept to prove the effectiveness of the choice on encapsulating 
the instance to a general `AbstractFactory`. The `PaddleModel` class contains a general model for each paddle in the 
game. Much like the balls in game there are no real difference between the paddles other than its size and speed. 
However, deciding to add more functionality to a certain paddle is straight forward with how the structure is right 
now. The structure of these paddle factories and models(products) adheres to the Factory Method Pattern where there 
is a single abstract method used by concrete factories for creating a product of specific type.

**powerup package**

The powerup package contains logic about powerups that might spawn when a brick is destroyed. Finding a suitable 
placement for the Object Pool Pattern was found difficult in the beginning of the development process but then 
the idea of powerups came which ended up being used within the game. Naturally by utilizing the OP design pattern, the 
Consumer/Producer design pattern also had a way of fitting into this, when a powerup is supposed to be spawned it 
utilizes a consumer thread to consume from the pool of powerups. There is a class `ThreadPoolManager` which manages 
a pool of specific threads, more about that later.

So, the powerup structure actually follows the **Abstract Factory Pattern** which defines a way to create family of 
related or dependant objects. Although there is only one concrete factory utilized in this game as of now, there is 
no problem in adding another concrete factory which creates a different type of concrete product(powerup). For 
instance, there might be a concrete factory for each difficulty level that creates different types of 
difficult-specific products(powerups in this case). Adding a `HardPowerupFactory` which creates `HardBallPowerup` 
and `HardPaddlePowerup` is really not so hard in the way this is structured.

As said before, the concrete powerups resides inside a `LinkedBlockingQueue` (just to get a bit more 
synchronization) in the `PowerupPool` class which is a class managing the pool of powerup objects. This class offers 
methods for producing random concrete powerups by utilizing the `produceRandomPowerup`. This method utilizes the 
`java.util.Random` in order to randomly produce a specific powerup. The powerups produced are put into the pool and 
can be used within the game. 

If a powerup is not caught by a player and it goes of the screen, the powerup is simply returned to the pool in 
order to reuse it which in turn saves memory usage. You do not need to create an instance of powerups each time and 
then waste it when it is not caught.

There is also a `Consumer` class which consumes a powerup from the pool and returns a `Future`(or result) of the 
executed callable task. This Future can be used once the task is completed by using the `get()` method. The consumer 
is used within the `BallModel` when a ball hits a brick and a powerup should spawn.

Overall, the structure of the powerup package actually fulfills three requirements. It utilizes Abstract Factory to 
create BallPowerups and PaddlePowerups, Object Pool pattern where there is a pool of object ready to use within the 
game, as well as the Consumer/Producer pattern which consumes/produces powerups.


**BreakoutModel**

Besides the models for balls, bricks, paddles, and powerups, there is the `BreakoutModel` class which really is the 
core model of this application. It is used to drive the game forward by updating other models. It contains methods 
for retrieving specific models from within the game to be able to display them on the screen. A method which is 
specifically interesting in this class is the `checkPowerupSupply()` which utilizes syncrhonization when producing 
new powerups to the pool. There is a threshold for how small the pool of objects should be, if the pool dwindles and 
goes below the threshold a new producer task is scheduled within the `ThreadPoolManager` class to produce another 
powerup to the pool. Initially a **Race Condition** problem occurred where no synchronization between threads where 
used. Since multiple threads access a shared resource (The pool of powerup object) the pool always ended up with way 
more powerup objects than expected. The pool had up to 1000 objects inside the pool at once. In order to fix this 
synchronization was utilized within the pool object so one thread accessed it at once. The thread is put into a 
`waiting` state and releases its lock to another thread. When the thread has created the powerup object, all waiting 
threads are notified and may request to acquire the lock again.

This fulfills the requirements of at least three concurrent processes accessing a shared resource. Since the 
producer thread pool contains at least 4 threads that can execute tasks concurrently.

Another thing to note is that the `BreakoutModel` is a part of the Observer pattern where the `BreakoutModel` serves 
as the subject or observable and the `BreakoutController` is acting as an observer. This is used for game 
information such as time elapsed and when a brick is destroyed and the score is adjusted. The subject pushes the 
updates to the observer which updates the view. The `BreakoutModel` implements the `Subject` interface and overrides 
its methods for adding, removing and notifying observers.

**SetupModel**

this model is used for the setup screen where users choose their wanted level, music volume and more. This model 
contains data regarding the current music volume selected as well as a boolean value to check if the game is started 
or not. This is done to still adhere to the MVC architecture within the game setup screen, it contains model
(Setupmodel), view(SetupScreen), and controller (SetupScreenController).

**controllers package**

**SplashScreenController**

This is a controller for managing the SplashScreen view, it implements the `SplashScreenListener` interface to get 
updated once the splashscreen finishes loading and takes action accordingly.

**SetupController**

The SetupController is a controller instatiated from the `SplashScreenController` once the loading screen finishes. 
This controller manages the view and model for the setup screen where a user can choose a difficulty, adjust game 
volume, and read the game description. The SetupController instantiates a `SetupModel` and a `SetupScreen`. The 
controller has methods for changing background color, playing the setup sound and starting the game. It implements 
the `SetupScreenListener` and override its single method to invoke the `startGame()` function once the user presses the 
start-button.

**BreakoutController** 

The BreakoutController is what initializes the game and make updates to it with the use of Timer and `ActionListener`.
It implements three interfaces, namely **Observer**, **ActionListener**, and **KeyListener**. The first interface 
is used within the Observer pattern and contains a single abstract method `update()` which is used by the subject to 
update the observer. The `ActionListener` is used with the `Timer` in order to update the model/view every 16ms. 
This is as close to 60 fps as possible since the Timer only accepts integers as parameters. The last interface is 
used in order to register keystrokes within the `GamePanel` by utilizing the `BreakoutView::setKeyListener()` method. 


**Views package**

The views package contains GUI related classes to display the game to the user and is used so the user can interact 
with the game(move paddles) displaying the use of Event-driven UI where a user can press certain buttons/keys and an 
event happens in turn. The view package contain five different classes for different use. The `SplashScreen` is what 
is initially instantiated within the `SplashScreenController`. The use of this class is to 
present a visually appealing loading screen to the user. This was developed in order to use more Swing components 
and layout managers. It creates a new Thread for simulating the loading of the application, this is done so the EDT 
remains unblocked and responsive. When the thread is done it uses the `finally` in order to dispose itself and 
notify the `SplashScreenController` that the loading is done, since we are 
running the while loop in another thread(not EDT) we need to make sure the instantiation 
of the `SetupScreenController` is done on the EDT again in the SplashScreenController. The `SplashScreen` disposes 
releasing all the resources associated with this particular JWindow. 

The `SetupScreen` acts as the main menu in the game. It displays the name of the game and a number of buttons that 
users can click and interact with. This class extends the JFrame which apart from JWindow have a title bar with a 
specified name and buttons to minimize, maximize and close the window. It also offers various accessor methods for 
the `SetupScreenController` to utilize.

The `BreakoutView` is the main view for the application, it consists out of two panels: `GamePanel` which is 
responsible for showing data related to the overall game, and `InfoPanel` which handles the information for the game 
such as score, time, and bricks left. The `BreakoutView` is what the `BreakoutController` update with data from the 
`BreakoutModel`. The View then updates each panel accordingly by using the `repaintPanels()` and `updateInfoPanel()` 
methods.

**GamePanel**

As said before, the `GamePanel` class is responsible for displaying the game to the user, this includes paddle, ball,
and the field of bricks. It overrides the `paintComponent` method in order to paint the different objects on the 
screen. It uses methods from the `BreakoutView` to get access to versions of object that are up-to-date.

**InfoPanel**

The `InfoPanel` is responsible for displaying various information to the user such as the current time, score, and 
amount of bricks left in the game. It utilizes the `FlowLayout` manager to center the labels and creates a 
horizonal/vertical spacing  between them. It uses methods from the `BreakoutView` in order to get updated 
information just like the game panel does.

**support package**

The support package provides various classes that supports the game. It contains an `Appconfig` class which contains 
constants used within the game. It also handles outcomes by utilizing the **Template Method** where an abstract 
`GameOutcomeHandler` defines abstract methods that concrete outcome classes `GameWonHandler` and `GameLostHandler` 
implement. This package also contains a `SoundHandler` which handles all the sounds in the game, it utilizes the 
`ConcurrentHashMap` to map each `Sound` enum constant to a specific loaded `Clip`. It is used to play sounds when 
different objects collide.

The package also contains the `ThreadPoolManager` which initially resided inside the powerup package but was later 
moved to the support package since the manager handles sound on separate threads as well.

Lastly, the `Util` class is an enum class which provides the game with randomly generated colors based on hue, 
saturation, and luminance.


**EpicBreakout**

This is the entrance point of the application which simply instantiates a new `SplashScreenController` on the EDT using 
method reference.

## Discussion
### Purpose Fulfillment

The purpose stated have successfully been fulfilled for the B grade. The following should be implemented:

* Developing an Event Driven Application using GUI
* A minimum amount of four concurrent processes
* At least three processes accessing a shared resource in a threadsafe way
* A minimum amount of six unique Swing components
* At least four unique Swing layouts
* Five design patterns needed to be applied correctly
* At least two unique Streams API utilization's
* An application built using MVC architecture


**Developing an Event Driven Application using GUI**

The application is a fully functional Event driven application where the user have the control of what to do in the 
game. The user can choose the difficultly they like and controls game object paddle. The user can also close the 
game whenever they feel like stop playing.

**A minimum amount of four concurrent processes**

The game does indeed have at least four concurrent processes:

1. The EDT thread & The main thread
2. SplashScreen Thread
3. Consumer threadpool
4. Producer threadpool
5. Sound threadpool

There is also more threads created through the game which incorporates even more concurrency. The threads used 
within the application is well enough to consider the requirement fulfilled. After all there are also at least four 
threads residing within each of the threadpool also.

**At least three processes accessing a shared resource in a threadsafe way**

This is fulfilled particularly in the `BreakoutModel::checkPowerupSupply` where several tasks are executed 
concurrently and mutating the shared object pool. It works with wait() and notify() as well as synchronization to 
gain a lock. While implementing the synchronization of adding powerups to the pool a deadlock problem where 
introduced. The problem was because of two different locks being shared between threads which caused threads to wait 
for each other in all eternity. The threads acquired a lock on the `PowerupPool` instance variable within the 
`BreakoutModel` while acquiring another lock inside the `PowerupPool` class on the `LinkedBlockingQueue`. This of 
course is no good in a concurrent application since two threads acquire a lock each and then wait for one another to 
release the lock, ultimately leading to a deadlock. The reason this happened was because of a small mistake while 
implementing the synchronization logic. Since the instance variable `powerupPool` inside the `BreakoutModel` refers 
to the `PowerupPool` class instance, and not the actual `LinkedBlockingQueue` inside the `PowerupPool` class. In 
order to fix this the `getPowerupPool()` method inside the `PowerupPool` class was utilized to gain access to the 
actual LinkedBlockingQueue.

**A minimum amount of six unique Swing components**

The application does indeed have at least six unique Swing components. Some components used within the game are 
JProgressBar, JLabel, JCheckbox, JButton, JSlider, and JRadioButton are in use. It was quite hard to fit all six 
components into the game since it really does not involve anything other than buttons and labels initially. So a 
JSlider had to be squeezed into the main menu screen in order to fulfill this criteria. This JSlider actually found a 
purpose in allowing the User to adjust the game music before starting the game which before was set to a static volume.

**At least four unique Swing layouts**

There are four layouts in use. The `SplashScreen` uses the `GridLayout`, `SetupScreen` uses `BoxLayout` and 
`InfoPanel` uses `FlowLayout`. `BorderLayout` is what is naturally used within frames so this is utilized in 
`BreakoutView` to position the panels. So in short these are used:

* GridLayout
* BoxLayout
* BorderLayout
* FlowLayout

**Five design patterns needed to be implemented**

The following design patterns are used within the game:

1. Object Pool
2. Producer/Consumer
3. Observer
4. Abstract Factory
5. Factory Method
6. Template Method

**Object Pool**

This pattern is utilized within the `PowerupPool` class where a number of concrete powerup objects are stored and 
can be accessed. It also defines methods for returning and producing new powerup object to be placed in the pool.

**Producer/Consumer**

This is utilized within the `ThreadPoolManager` where it defines a number of producer/consumer threads that 
ultimately produces or consumes powerups from/to the `PowerupPool`. The ThreadPoolManager is responsible for executing 
the producer and consumer tasks. The `Producer` and `Consumer` classes are the ones that actually consumes and 
produces powerups from the pool, the `Producer` implement the `Runnable` interface and override its `run()` method with 
concrete logic to produce powerups while the `Consumer` implements the `Callable` interface to be able to return a 
result of the consumed powerup object.

**Observer**

The observer pattern is in place between the `BreakoutModel` which acts as the subject and the `BreakoutController` 
which acts as the observer. The observer "subscribes" on the subject to get notified on changes. In case of this 
game, whenever information regarding time or score are changed. The `BreakoutModel::notifyObserver` utilizes the 
`notify()` method within the `Observer` interface. The breakout controller implements this observer interface and 
overrides its single abstract method.

**Abstract Factory**

The abstract factory is implemented within the powerup factories and concrete powerups. An interface called 
IPowerupFactory was implemented, which declares two abstract methods: addPaddlePowerup and addBallPowerup. These 
methods are responsible for producing specific types of powerups, these types are BallPowerup and PaddlePowerup.

To bring this design to life, the concrete factory PowerupFactory, which implements the IPowerupFactory interface is 
created. This factory is capable of creating and providing instances of concrete products - BallPowerup and 
PaddlePowerup.

This is what the Abstract Factory pattern dictates. It allows to create and manage families of related objects 
(powerups) without having to specify their concrete classes explicitly.


**Factory Method**

The factory method design pattern is implemented specifically in the ball factories which implements a single method 
for creating concrete balls. It is very similar to the Abstract Factory but does not create families of related 
object but instead a single object. The `BallFactory` provides a abstract method for creating a ball which concrete 
factories can implement to create concrete balls.

**Template Method**

The placement of the template method was not as obvious as some other design patterns, after developing the game 
there were only five design patterns used. In order to implement the sixth design pattern a template method 
for showcasing game outcomes where developed. It consists out of the abstract class `GameOutcomeHandler` which 
defines a template method for handling game outcomes. There are two concrete subclasses which implements the method 
with its specific logic. For example the `GameWonHandler` displays a certain String message and plays a winning 
sound while the `GameLostHandler` specifies the methods with a message and sound specific to losing a game.

All the design patterns above are integrated into the application and thus fulfills the requirement for B.

**At least three unique Streams API utilizations**

There is at least three unique Streams API utilizations inside the application, here is a list of three of them:

1. BrickField::getBrick

This method uses the IntStream in order to find a matching Brick object to an index provided. It is similar to this

```java
public Brick getBrick(int index) {
    for (int i = 0; i < brickArray.length; i++) {
        if (i == index && brickArray[i] != null) {
            return brickArray[i];
        }
    }
    return null;
}
```

2. BrickField::getRenderableBricks

This method uses the `Arrays.stream` to create a stream from the brickArray and then filtering the bricks based on 
if the elements are not null. If elements are null they are removed from the list. This is then created into a list 
again using the `collect` with the specified `Collectors.toList()`.

3. BrickField::remove

The last method removes a brick based on certain criteria. It again utilizes the `Intstream` to create indices of 
the brickArray it is then filtered to match the index provided in the parameters. The use of `findFirst` is used to 
find the first element of this stream. The `ifPresent` is used to perform a given action if the element exists, in 
this case we get the brick object associated with the element and set it to null and subtract one from the total 
amount of bricks left.

**An application built using MVC architecture**

This application fulfills the MVC pattern since it separate the model from the view, they do not have any notion of 
each other's existence. All communication goes through the `BreaoutController`.

### Alternative Approaches


**Abstraction**

The application showcases some use of abstraction between factories but it lacks abstraction between the models. An 
approach that came to mind while developing was to create interfaces specific for each entity in the game. There are 
basically two entities inside this game: mobile entities and static entities. A way to abstract the logic and get a 
better looking structure would be to create these interfaces that contains methods shared among all static entities
(bricks) and all mobile entities(ball, paddle, powerup). Implementing an `IStaticEnity` and an `IMobileEntity` and 
having concrete entities implement specific logic to the methods would be much better than it is right now. 
Specifically inside the mobile entities and its method to move is not abstracted. This approach would also promote 
code reusability and follows the DRY principle.

**Logic in the game**

The game is not complete in whole due to the time restriction, there is currently no option for the users to restart 
the game or return to the main screen when a game is lost/won. There is also a problem with how the ball collides 
with paddle, if the ball collides with the paddle and the ball have passed a certain point it will break the logic 
of the ball, and it will get stuck inside the paddle. A way to fix this is to fix collision with the paddle and 
change direction of the ball accordingly, perhaps by first changing the balls X-position and then check if the ball 
collided with any object and then do the same for the Y-axis. This would allow for checks in certain directions 
instead how it is implemented now where the ball first moves in both X and Y-axis and then checks for collisions. But 
this is simply not prioritized right now because of the time restrictions. 

Another big drawback in the game logic is the pause functionality. While it does pause the game and stops the change 
in model data, when the user resumes the game the games timer jumps in time. This is due to the use of `System.
currentTimeMillis()` which does not care if the game is paused, it will continue counting even if the game is paused.
A fix to this would be to get a counter for when the game is paused and simply subtracting it from the total elapsed 
time when the game is resumed.

**Better score logic**

As of now, the score is simply just added with 50 when a brick is destroyed which is quite boring due to the fact 
that all users who have completed the game will have the same score which is 84(amount of bricks) times 50. A way to 
make this funnier would be to implement a powerup that multiplies the score gotten during a specified amount of time 
and the use of time penalty where the user gets a penalty based on elapsed time and destoryed bricks and might even 
gain points if many bricks are destroyed within a short period of time. Another fun way would be to add combo 
multipliers where the score multiplies with the amount of bricks destroyed in one "turn".

**Storing user score**

For future development of this breakout clone, the availability for users to store their time and score will be 
implemented.

**Different game characteristic**

Another thought that came to mind would be to have a set amount of time, lets say 30 seconds, that ticks down 
towards zero. The goal of the game would be to try to survive as long as possible and get as many seconds left on the 
clock as possible when the game is won. The use of powerups would be changed to giving the user some amount of time 
per "powerup" caught. This would also be a fun game and might be an application to develop for fun.

Another idea is to change the powerup logic all toghether and instead of being powerups, thhey are extra balls. 
Catching them will spawn a new ball, this would make the game more entertaining since there are more balls to manage.

## Personal Reflections
This project have been very fun and rewarding for me. I have finally gotten ideas on what to create on my spare time 
with this new knowledge. Developing different games in java on my spare time will be very fun and benefit my Java 
knowledge a lot!