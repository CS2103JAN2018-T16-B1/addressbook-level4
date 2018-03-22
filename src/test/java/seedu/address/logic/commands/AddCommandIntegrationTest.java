package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalCatalogue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCatalogue(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Book validBook = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.addPerson(validBook);

        assertCommandSuccess(prepareCommand(validBook, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validBook), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Book bookInList = model.getCatalogue().getPersonList().get(0);
        assertCommandFailure(prepareCommand(bookInList, model), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code book} into the {@code model}.
     */
    private AddCommand prepareCommand(Book book, Model model) {
        AddCommand command = new AddCommand(book);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
