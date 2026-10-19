import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; 
import com.mycompany.assignment2.Assignment2;

public class Assignment2Test {
    
    
    @Test
    public void testUserNameValid() { 
         
        Assignment2.Login login = 
                new Assignment2.Login( 
                "Kyle",
                "Crane",
                "kyl_1",
                "Pass@123",       
                "+27838968976");
        
        assertTrue(login.checkUserName());
    }
}