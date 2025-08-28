package mip;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    @Test
    public void parseDeadlineDescription_validCommand_validDescription() {
        try {
            assertEquals("do things",
                    Parser.getDeadlineDescription("deadline do things /by sunday"));

            assertEquals("do many complicated things!!",
                    Parser.getDeadlineDescription("deadline do many complicated things!! /by sunday"));
        } catch (Exception e) {
            fail();
        }
    }
    @Test
    public void parseDeadlineDescription_noDue_exceptionThrown() {
        try {
            assertEquals("", Parser.getDeadlineDescription("deadline eat things"));
            fail();
        } catch (MipException e) {
            assertEquals("A deadline must have '/by' followed by a time.", e.getMessage());
        }
    }

    @Test
    public void parseEventDescription_validCommand_validDescription() {
        try {
            assertEquals("do things",
                    Parser.getEventDescription("event do things /from sunday /to monday"));

            assertEquals("do many complicated things!!",
                    Parser.getEventDescription("event do many complicated things!! /from now /to later"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseEventFrom_noFrom_exceptionThrown() {
        try {
            assertEquals("", Parser.getEventDescription("event do things"));
            fail();
        } catch (MipException e) {
            assertEquals("An event must have '/from' followed by start time.", e.getMessage());
        }
    }

}
