import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ServerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher view = req.getRequestDispatcher("index.jsp");
        view.forward(req, resp);
    }

    private String readFile(String path) {
        BufferedReader br;
        String result = "";
        try {
            br = new BufferedReader(new FileReader(path));
            try {
                String x;
                while ( (x = br.readLine()) != null ) {
                    result += x;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return result;
    }

    public void removeElement(Object[] a, int del) {
        System.arraycopy(a,del+1,a,del,a.length-1-del);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String opt = req.getParameter("opt");
        String data = readFile("temperature.txt");
        String[] temps = data.split(",");
        String result = "";
        req.setCharacterEncoding("UTF-8");
        if (opt.equals("1")) {
            int sum = 0;
            for (int i = 0; i < temps.length; i++)
                sum += Integer.parseInt(temps[i]);
            sum /= temps.length;
            result = "<h3>The avarage temperature - <strong>" + sum + "</strong></h3>";
        }
        else if (opt.equals("2")) {
            int count = 0;
            for (int i = 0; i < temps.length; i++)
                if (Integer.parseInt(temps[i]) < 0)
                    count += 1;
            result = "<h3>Count days when the temperature less then 0 - <strong>" + count + "</strong></h3>";;
        }
        else if (opt.equals("3")) {
            System.out.println("there");
            int[] count = new int[3];
            for (int i = 0; i < count.length; i++) {
                int pos = 0;
                count[i] = Integer.parseInt(temps[0]);
                for (int j = 0; j < temps.length; j++) {
                    if (Integer.parseInt(temps[j]) > count[i]) {
                        if (i > 0 && count[i] > count[i-1])
                            count[i] = j + i + 1;
                        else
                            count[i] =  j + 1;
                        pos = j;
                    }
                }

                removeElement(temps, pos);
            }
            result = "<h3>The warmer days:<br></h3>";
            for (int i = 0; i < count.length; i++)
                result += "<strong>" + count[i] + "</strong><br>";
        }

        req.setAttribute("result", result);
        RequestDispatcher view = req.getRequestDispatcher("result.jsp");
        view.forward(req, resp);
    }
}