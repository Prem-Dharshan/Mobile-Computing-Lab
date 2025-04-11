Sure! Here's a quick reference for **`for` loop**, **`while` loop**, and **`if-else` / `else-if`** in **Kotlin** (used in Android too):

---

### ✅ `for` loop
```kotlin
// Loop through a range
for (i in 1..5) {
    println("i is $i")
}

// Loop through a list
val names = listOf("Alice", "Bob", "Charlie")
for (name in names) {
    println(name)
}

// Loop with index
for ((index, value) in names.withIndex()) {
    println("Index: $index, Value: $value")
}
```

---

### ✅ `while` loop
```kotlin
var x = 0
while (x < 5) {
    println("x is $x")
    x++
}
```

---

### ✅ `do-while` loop
```kotlin
var y = 0
do {
    println("y is $y")
    y++
} while (y < 5)
```

---

### ✅ `if`, `else if`, `else`
```kotlin
val number = 10

if (number < 0) {
    println("Negative")
} else if (number == 0) {
    println("Zero")
} else {
    println("Positive")
}
```

---

Let me know if you want these inside an Android button click or UI logic example — happy to drop it into context!