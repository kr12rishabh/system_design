## Summary

Dekh bhai, ye code **tree ko level by level traverse** karta hai.
Aur har next level par direction change karta hai.

Matlab:

```text
Level 0 -> left to right
Level 1 -> right to left
Level 2 -> left to right
Level 3 -> right to left
```

Isi ko **zigzag traversal** bolte hain.

---

## One-line answer

Bhai, queue se level order traversal kar, har level ki list bana, agar direction right-to-left hai to list reverse kar de, fir direction toggle kar de.

---

## Code

```java
import java.util.*;

class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }

        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);

        boolean leftTraversal = true;

        while (!q.isEmpty()) {
            List<Integer> tempVal = new ArrayList<>();
            int sz = q.size();

            for (int i = 0; i < sz; i++) {
                TreeNode current = q.poll();

                tempVal.add(current.val);

                if (current.left != null) {
                    q.add(current.left);
                }

                if (current.right != null) {
                    q.add(current.right);
                }
            }

            if (!leftTraversal) {
                Collections.reverse(tempVal);
            }

            ans.add(tempVal);

            leftTraversal = !leftTraversal;
        }

        return ans;
    }
}
```

---

## Full Explanation In Hinglish

Dekh bhai, sabse pehle ye samajh:

Normal level order traversal me hum tree ko aise print karte hain:

```text
Level 0: left to right
Level 1: left to right
Level 2: left to right
```

But zigzag me scene thoda different hai.

Zigzag me direction har level ke baad change hoti hai.

```text
Level 0: left to right
Level 1: right to left
Level 2: left to right
Level 3: right to left
```

Isliye hume ek boolean chahiye jo bataye ki abhi direction kya hai.

---

## Step 1: Answer List

```java
List<List<Integer>> ans = new ArrayList<>();
```

Bhai, ye final answer store karega.

Example:

```text
[
  [3],
  [20, 9],
  [15, 7]
]
```

Har inner list ek level ko represent karti hai.

---

## Step 2: Null Check

```java
if (root == null) {
    return ans;
}
```

Dekh bhai, agar tree khali hai, matlab root hi null hai, to kuch traverse karne ko hai hi nahi.

Isliye empty list return kar di.

---

## Step 3: Queue Banana

```java
Queue<TreeNode> q = new ArrayDeque<>();
q.add(root);
```

Bhai, level order traversal ke liye queue use hoti hai.

Queue ka simple funda hai:

```text
jo pehle aaya, wo pehle niklega
```

Isse hum tree ko level by level process kar paate hain.

Starting me queue me root daal diya.

---

## Step 4: Direction Variable

```java
boolean leftTraversal = true;
```

Bhai, ye sabse important variable hai.

```text
true  ka matlab -> left to right
false ka matlab -> right to left
```

First level hamesha left to right hota hai.

Isliye initially:

```java
leftTraversal = true;
```

---

## Step 5: Jab Tak Queue Empty Nahi Hoti

```java
while (!q.isEmpty()) {
```

Bhai, jab tak queue me nodes hain, tab tak traversal chalega.

Har while loop ek complete level ko process karega.

---

## Step 6: Current Level Ki List

```java
List<Integer> tempVal = new ArrayList<>();
int sz = q.size();
```

Bhai, `tempVal` me current level ke node values store honge.

Aur `sz` me current level ke nodes ki count store hogi.

Ye `sz` bahut important hai.

Kyuki jab hum current level process karenge, tab hum next level ke children ko bhi queue me add karenge.

Agar size pehle fix nahi kiya, to current level aur next level mix ho jayenge.

---

## Example Samajh

Tree ye hai:

```text
        3
      /   \
     9     20
          /  \
         15   7
```

Starting:

```text
Queue = [3]
leftTraversal = true
```

Ab current level ka size:

```text
sz = 1
```

Matlab is level me sirf ek node hai: `3`.

---

## Step 7: Current Level Ke Nodes Process Karna

```java
for (int i = 0; i < sz; i++) {
    TreeNode current = q.poll();

    tempVal.add(current.val);

    if (current.left != null) {
        q.add(current.left);
    }

    if (current.right != null) {
        q.add(current.right);
    }
}
```

Bhai, is loop me hum ek level ke saare nodes process kar rahe hain.

Har node ke liye:

```text
1. Queue se node nikalo
2. Uski value tempVal me daalo
3. Uska left child hai to queue me daalo
4. Uska right child hai to queue me daalo
```

---

## Level 0 Dry Run

Queue:

```text
[3]
```

Node nikala:

```text
current = 3
```

Value add kari:

```text
tempVal = [3]
```

Children add kiye:

```text
Queue = [9, 20]
```

Ab direction true hai, matlab left to right.

To reverse karne ki zarurat nahi.

```text
ans = [[3]]
```

Fir toggle:

```java
leftTraversal = !leftTraversal;
```

Pehle true tha, ab false ho jayega.

```text
leftTraversal = false
```

---

## Step 8: Reverse Kab Karna Hai?

```java
if (!leftTraversal) {
    Collections.reverse(tempVal);
}
```

Bhai, jab `leftTraversal` false ho, tab iska matlab current level right to left print hona chahiye.

Par hum queue me nodes normal left to right order me hi daalte hain.

Isliye simple trick:

```text
pehle current level normal collect kar lo
fir agar direction right to left hai to list reverse kar do
```

---

## Level 1 Dry Run

Queue ab hai:

```text
[9, 20]
```

Current level size:

```text
sz = 2
```

Process 9:

```text
tempVal = [9]
```

Process 20:

```text
tempVal = [9, 20]
```

20 ke children add honge:

```text
Queue = [15, 7]
```

Ab direction false hai.

Matlab is level ko right to left chahiye.

So reverse:

```text
[9, 20] -> [20, 9]
```

Answer:

```text
ans = [[3], [20, 9]]
```

Ab toggle:

```text
leftTraversal = true
```

---

## Level 2 Dry Run

Queue:

```text
[15, 7]
```

Current level size:

```text
sz = 2
```

Process 15:

```text
tempVal = [15]
```

Process 7:

```text
tempVal = [15, 7]
```

Ab direction true hai.

Matlab left to right.

Reverse nahi karna.

Answer:

```text
ans = [[3], [20, 9], [15, 7]]
```

Final output:

```text
[[3], [20, 9], [15, 7]]
```

---

## Toggle Kaha Aur Kyu?

Ye line:

```java
leftTraversal = !leftTraversal;
```

Bhai, ye **level complete hone ke baad** hi karni hai.

Isliye ye line yaha sahi hai:

```java
ans.add(tempVal);
leftTraversal = !leftTraversal;
```

Agar tu toggle `for` loop ke andar karega, to direction har node ke baad change hogi.

Wo galat ho jayega.

Direction node by node nahi badalni.

Direction level by level badalni hai.

---

## Important Point

Ye galat soch hai:

```text
har node ke baad direction change karni hai
```

Ye sahi soch hai:

```text
har level ke baad direction change karni hai
```

---

## Time Complexity

```text
O(n)
```

Bhai, har node ko ek baar visit kar rahe hain.

So time complexity `O(n)` hai.

---

## Space Complexity

```text
O(n)
```

Queue me worst case me ek level ke bahut saare nodes aa sakte hain.

Aur answer list me bhi saare nodes store honge.

So space complexity `O(n)` hai.

---

## Interview Version

Bhai interview me aise bolna:

We can solve this using BFS. I use a queue to process the tree level by level. For each level, I store node values in a temporary list. I keep one boolean variable to track direction. If the direction is right to left, I reverse the current level list before adding it to the answer. After each level, I toggle the direction. This gives zigzag level order traversal.
