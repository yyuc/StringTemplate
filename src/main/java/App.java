/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {

    public static void main(String[] args) {
        String s = "Hey,{{person.name}}, {{person.address.city}} is my home";
        new Parser(s).Build();
    }
}
