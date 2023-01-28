import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.*;

public class MyJDBC {
    public static void main(String[] args)
    {
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool", "root", "roeid1928");
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            Scanner scanner = new Scanner(System.in);
            int num=0;
            double sum=0;
            int count = 0;
            while (num!=8)
            {
                System.out.println("\nHi there!\nplease enter a number:\n1 - avg of all students" +
                        "\n2 - avg of all Male students\n3 - avg of all Female students" +
                        "\n4 - avg of all students that above 2 meter and hve purple car" +
                        "\n5 - entering ID and getting his friends and the friends of his friends" +
                        "\n6 - get the percentage of the students by how many friends they have" +
                        "\n7 - get the avg of grades by ID\n8 - end program");
                sum=0;
                count = 0;
                num = scanner.nextInt();
                switch (num)
                {
                    case 1:
                    {
                        resultSet = statement.executeQuery("select avg(grade_avg) as avg_g from highschool");
                        while (resultSet.next()) {
                            System.out.println(resultSet.getDouble("avg_g"));
                        }
                        break;
                    }
                    case 2:
                    {
                        resultSet = statement.executeQuery("select avg(grade_avg) as avg_g from highschool where gender = 'Male'");
                        while (resultSet.next()) {
                            System.out.println(resultSet.getDouble("avg_g"));
                        }
                        break;
                    }
                    case 3:
                    {
                        resultSet = statement.executeQuery("select avg(grade_avg) as avg_g from highschool where gender = 'Female'");
                        while (resultSet.next()) {
                            System.out.println(resultSet.getDouble("avg_g"));
                        }
                        break;
                    }
                    case 4:
                    {
                        resultSet = statement.executeQuery("select avg(cm_height) as avg_h from highschool where cm_height >199 and car_color = 'Purple' and has_car = 'true'");
                        while (resultSet.next()) {
                            System.out.println(resultSet.getDouble("avg_h"));
                        }
                        break;
                    }
                    case 5:
                    {
                        System.out.println("enter ID");
                        String Id = scanner.next();
                        String friends_Id []= new String[10];
                        int counter =0;
                        resultSet = statement.executeQuery("select * from highschool_friendships where friend_id = "+Id +" and other_friend_id <> 0");
                        System.out.println("his friends ID:");
                        while (resultSet.next()) {
                            friends_Id[counter++]=resultSet.getString("other_friend_id");
                            System.out.println(resultSet.getInt("other_friend_id"));
                        }
                        resultSet = statement.executeQuery("select * from highschool_friendships where other_friend_id = "+Id+" and friend_id <> 0");
                        while (resultSet.next()) {
                            friends_Id[counter++]=resultSet.getString("friend_id");
                            System.out.println(resultSet.getInt("friend_id"));
                        }
                        System.out.println("friends of friends ID:");
                        for(int i=0;i<counter;i++)
                        {
                            resultSet = statement.executeQuery("select * from highschool_friendships where friend_id = "+friends_Id[i]+" and other_friend_id <> 0 and other_friend_id <> "+Id);
                            while (resultSet.next()) {
                                System.out.println(resultSet.getInt("other_friend_id"));
                            }
                            resultSet = statement.executeQuery("select * from highschool_friendships where other_friend_id = "+friends_Id[i]+" and friend_id <> 0 and friend_id <> "+Id);
                            while (resultSet.next()) {
                                System.out.println(resultSet.getInt("friend_id"));
                            }
                        }
                        break;
                    }
                    case 6:
                    {
                        resultSet = statement.executeQuery("WITH friend_counts AS (\n" +
                                "  SELECT \n" +
                                "    idd, \n" +
                                "    (SELECT COUNT(*) FROM highschool.highschool_friendships WHERE (friend_id = idd OR other_friend_id = idd) and (idd <> 0)) as number_of_friends\n" +
                                "  FROM \n" +
                                "    (SELECT DISTINCT friend_id as idd FROM highschool.highschool_friendships\n" +
                                "    UNION\n" +
                                "    SELECT DISTINCT other_friend_id FROM highschool.highschool_friendships) as ids\n" +
                                ")\n" +
                                "\n" +
                                "SELECT \n" +
                                "  number_of_friends, \n" +
                                "  COUNT(*) as total, \n" +
                                "  ROUND(COUNT(*)/(SELECT COUNT(*) FROM friend_counts)*100, 2) as percentage\n" +
                                "FROM friend_counts\n" +
                                "GROUP BY number_of_friends\n" +
                                "ORDER BY number_of_friends;");
                        double sump=0;
                        double sumonep=0;
                        int counter = 0;
                        while (resultSet.next()) {
                            if(resultSet.getInt("number_of_friends")==1)
                            {
                                sumonep=resultSet.getDouble("percentage");
                                System.out.println("the percentage of students that have one friend is: "+sumonep+"%");
                            }
                            else
                            {
                                if(resultSet.getInt("number_of_friends")!=0)
                                {
                                sump += resultSet.getDouble("percentage");
                                }
                            }
                        }
                        System.out.println("the percentage of students that have two and above friends is: "+sump+"%");
                        System.out.println("the percentage of students that have no friends is: "+(100-sump-sumonep)+"%");
                        break;
                    }
                    case 7:
                    {
                        System.out.println("enter identification_card");
                        String Id = scanner.next();
                        resultSet = statement.executeQuery("SELECT grade_avg FROM new_view WHERE identification_card = " + Id);
                        while (resultSet.next()) {
                            System.out.println(resultSet.getDouble("grade_avg"));
                        }
                        break;
                    }
                    case 8:
                    {
                        System.out.println("end of program");
                        break;
                    }
                    default:
                    {
                        System.out.println("you entered wrong number try again");
                        break;
                    }
                }
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
