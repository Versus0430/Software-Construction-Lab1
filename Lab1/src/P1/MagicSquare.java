package P1;


import java.io.*;
import java.util.Scanner;

public class MagicSquare {
    public static int[][] sqr = new int[200][200];

    public static boolean isLegalMagicSquare(String filename) throws IOException
    {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))){
            String line = "";
            int l=0,w=0,i=0;
            int[] t = new int[200];
            int sum1=0, sum2=0, sum3=0;
            int x=0, y=0, z=0;
            while((line = in.readLine()) != null)
            {
                String[] s = line.split("\t"); //以\t对line进行分割，并返回字符串给s
                w = s.length;
                t[l] = w;
                if(l>=1)
                {
                    if(t[l] != t[l-1])
                    {
                        System.out.println("有数字未用/t分隔或某一行/列缺少数字");
                        return false;
                    }
                }
                for(i=0; i<w; i++)
                {
                    try{
                        sqr[l][i] = Integer.valueOf(s[i].trim());
                    }catch(NumberFormatException e1)
                    {
                        System.out.println("存在非正整数");
                        //return false;
                    }
                    if(sqr[l][i] <= 0)
                    {
                        System.out.println("存在非正整数");
                        return false;
                    }
                }
                l++;
            }
            if(l != w)  //行列数不相等
            {
                System.out.println("行列数不相等");
                return false;
            }
            for(int j=0; j<w; j++)
            {
                x += sqr[0][j]; //x为基准值
                y += sqr[j][0]; //y为基准值
            }
            if(x != y) {
                System.out.println("行列和不等");
                return false;
            }
            for(i=1; i<w; i++)
            {
                sum1=sum2=sum3=0;
                for (int j = 0; j<w; j++)
                {
                    sum1 += sqr[i][j];
                    sum2 += sqr[j][i];
                    sum3 += sqr[j][j];
                }
                if (sum1 != x || sum2 != y || sum3 != x) //行或列或对角线的和不等
                {
                    System.out.println("存在行或列或对角线的和不等");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean generateMagicSquare(int n) throws FileNotFoundException {
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;

        for(i=1; i<=square; i++)
        {
            magic[row][col]=i;
            if(i % n == 0) //每填充n个数，行数加一
            {
                row++;
            }
            else
            {
                if(row == 0) //若行数为0时，令行数为n-1
                    row = n - 1;
                else //否则，行数减一
                    row--;
                if(col == (n - 1)) //若列数等于n-1时，令列数等于0
                    col = 0;
                else //否则，列数加一
                    col++;
            }
        }

        for(i=0; i<n; i++)
        {
            for(j=0; j<n; j++)
            {
                System.out.print(magic[i][j] + "\t");
            }
            System.out.println();
        }

        File file = new File("c:/github/HIT-Lab1-1190501809/src/P1/txt/6.txt");
        PrintWriter writer = new PrintWriter(file);
        for(i=0; i<n; i++)
        {
            for (j = 0; j < n; j++)
            {
                writer.print(magic[i][j] + "\t");
            }
            writer.println();
        }
        writer.close();

        return true;
    }

    public static void main(String[] args) throws IOException {
        boolean[] a = new boolean[6];
        int i;
        char j='1';
        for(i=1; i<6; i++) {
            a[i] = isLegalMagicSquare("c:/github/HIT-Lab1-1190501809/src/P1/txt/" + j + ".txt");
            if (a[i] == false) {
                System.out.println(i + ".txt:" + "Not MagicSquare!");
            }
            else
                System.out.println(i + ".txt:" + "MagicSquare!");
            j++;
        }

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if(n <= 0 || n % 2 == 0) //n为负数或偶数时，报错并结束程序
        {
            System.out.println("Input error!");
            System.exit(0);
        }
        generateMagicSquare(n);
        if(isLegalMagicSquare("c:/github/HIT-Lab1-1190501809/src/P1/txt/6.txt") == false)
            System.out.println("6.txt:"+"Not MagicSquare!");
        else
            System.out.println("6.txt:"+"MagicSquare!");
    }
}
