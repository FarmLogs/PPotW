class Quine{
    public static void Main(){
        string quino="class Quine{{public static void Main(){{string quino={0}{1}{0};System.Console.Write(quino,'{0}',quino);}}}}";
        System.Console.Write(quino,'"',quino);
    }
}
