import java.util.Scanner;
import java.util.Stack;

public class Program {
    public static boolean isOperator(String c) {
        return (c.equals("+") || c.equals("-"));
    }

    public static Node build(String[] s) {
        Stack<Node> nodeStack = new Stack<>();
        Stack<String> stringStack = new Stack<>();
        Node t;

        for (String value : s) {
            if (value.equals("(")) {
                stringStack.add(value);
            } else if (!value.equals(")") && !isOperator(value)) {
                t = new Node(value);
                nodeStack.add(t);
            } else if (isOperator(value)) {
                while (!stringStack.isEmpty() && !stringStack.peek().equals("("))
                //&& ((s.charAt(i) != '^' && p[stC.peek()] >= p[s.charAt(i)])
                //                    || (s.charAt(i) == '^'
                //                        && p[stC.peek()] > p[s.charAt(i)]))
                {
                    createNode(nodeStack, stringStack);
                }
                stringStack.push(value);
            } else if (value.equals(")")) {
                while (!stringStack.isEmpty() && !stringStack.peek().equals("(")) {
                    createNode(nodeStack, stringStack);
                }
                stringStack.pop();
            }
        }
        return nodeStack.peek();
    }

    private static void createNode(Stack<Node> nodeStack, Stack<String> stringStack) {
        Node t;
        Node t1;
        Node t2;
        t = new Node(stringStack.peek());
        stringStack.pop();
        t1 = nodeStack.peek();
        nodeStack.pop();
        t2 = nodeStack.peek();
        nodeStack.pop();
        t.left = t2;
        t.right = t1;
        nodeStack.add(t);
    }

    public static double calculateR(Node root) {
        if (root.data.equals("+")) {
            return calculateR(root.left) + calculateR(root.right);
        } else if (root.data.equals("-")) {
            double l = calculateR(root.left);
            double r = calculateR(root.right);
            return (l*r)/(l+r);
        } else {
            return Double.parseDouble(root.data);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        //String a = "((R1+R2)-((R3+R4)-R5))";
        String str = "(" + a + ")";
        String[] arr = str.split("((?<=;)|(?=;)|(?<=\\+)|(?=\\+)|(?<=-)|(?=-)|(?<=\\()|(?=\\()|(?<=\\))|(?=\\)))");
        Node root = build(arr);
        double q = calculateR(root);
        System.out.println(q);
    }
}
