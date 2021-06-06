import java.io.*;

public final class Main
{
    public static void main(String[] argss) throws InterruptedException
    {
        String[] args = {"C:\\My Documents\\hui.txt"};

        if (args.length == 0)
        {
            System.out.println("No file provided. Nothing to do 😊");
            Thread.sleep(1000);
            return;
        }

        String filename = args[0];
        String out_filename = filename + "_out.txt";

        try (
            var reader = new BufferedReader(new FileReader(filename));
            var writer = new BufferedWriter(new FileWriter(out_filename))
        )
        {
            for (String line = reader.readLine(); line != null; line = reader.readLine())
            {
                writer.append(NumbersFromRussian.process(line).toString());
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found exception!\n" + e.getMessage());
            Thread.sleep(1000);
        }
        catch (IOException e)
        {
            System.out.println("IO exception!\n" + e.getMessage());
            Thread.sleep(1000);
        }

        System.out.println("Finished! Result:" + out_filename);
        Thread.sleep(1000);
    }
}
