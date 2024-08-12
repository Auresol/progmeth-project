# Progmeth final
## HashMap
syntax
- `HashMap<K, V>`: A map that maps keys of type `K` to values of type `V`.
- `HashMap.put(K, V)`: Put a key-value pair into the map.
- `HashMap.get(K)`: Get the value associated with a key.
- `HashMap.containsKey(K)`: Check if the map contains a key.
- `HashMap.remove(K)`: Remove a key-value pair from the map.

## installize cosnst array
- `static final int[] NAME;` declare
- `NAME = new int[]{1, 2, 3};` initialize

## javafx
### ตัว extends
- extends จาก class
- GridPane (ตาราง ใช้ this.add(ITEM, row, col, rowspan, colspan) ), VBox (แนวตั้ง ใช้ getChildren.add()), HBox (แนวนอน ใช้ getChildren.add())
- มี getChildren().add(node) เพื่อเพิ่ม node ลงไปใน pane
- มี getChildren().remove(node) เพื่อลบ node ออกจาก pane
- มี getChildren().clear() เพื่อลบทุก node ออกจาก pane
- ตย.
``` java
public class somePane extends GridPane {
    public somePane() {
        Button btn = new Button("Click me");
        this.getChildren().add(btn);
    }
}
```
### โหลด resource
- โหลด resource จาก file ที่อยู่ใน project
``` java
Image img = new Image(ClassLoader.getSystemResource(path).toString());
```

### event handler
- declare first -> 
``` java
EventHandler<EVENT_TYPE>() handler = new EventHandler<EVENT_TYPE>() {
  @Override
  public void handle(EVENT_TYPE event) {
      System.out.println("Hello World!");
  }
});
```
EVENT_TYPE = MouseEvent, KeyEvent, ActionEvent, etc. (แล้วแต่ event ที่จะใช้)
- อีก syntax
``` java
EventHandler<EVENT_TYPE>() handler = (event) -> {
    System.out.println("Hello World!");
});
```
- ตั้งค่าให้ node ด้วย node.setOn<span style="color:green">EVENT_TYPE</span>(handler);

## Threads
- สร้าง thread ใหม่จาก class ที่ไม่ได้ extends Thread
``` java
Thread thread = new Thread(() -> {
    try {
        somefunction();
    } catch (InterruptedException e) {};
});

thread.start(); // สั่งให้ thread เริ่มทำงาน
```
- สั่งให้ thread หยุดทำงาน x วินาที
``` java
Thread.sleep(miliseconds); // หยุดทำงาน x วินาที ใน thread ปัจจุบัน
```
การสั่ง thread.sleep ทำให้ thread ปัจจุันหยุดทำงาน (ถ้าสั่งเปล่าๆ จะเป็น thread ของ instance ตัวหลักของคลาส)
เลยต้องใช้ thread.sleep ในฟังก์ชั่นที่ถูกส่งเข้าไปใน thread ใหม่ด้วย new Thread()

## Threads with javafx
- ไม่สามารถสั่งเปลี่ยนของใน javafx จาก thread ตรงๆได้ (เพราะ javafx มี thread แยก)
- ต้องใช้ Platform.runLater(() -> { /* สั่งเปลี่ยนของใน javafx ได้ */ });
``` java
Platform.runLater(new Runnable() {
    @Override
    public void run() {
        // Update UI here
    }
});
```
run() เป็นฟังก์ชั่นที่ต้องมีใน Runnable และจะถูกเรียกเมื่อ Platform.runLater ถูกเรียก


