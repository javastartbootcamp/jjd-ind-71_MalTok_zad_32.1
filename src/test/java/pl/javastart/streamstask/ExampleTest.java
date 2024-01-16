package pl.javastart.streamstask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ExampleTest {

    private StreamsTask streamsTask;
    private List<User> users;
    private List<Expense> expenses;

    @BeforeEach
    public void init() {
        streamsTask = new StreamsTask();
        users = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    @Test
    public void shouldReturn2ForWomenCollectionSize() {
        // given
        users.add(new User(1L, "Alicja", 20));
        users.add(new User(2L, "Dominik", 15));
        users.add(new User(3L, "Patrycja", 25));

        // when
        Collection<User> women = streamsTask.findWomen(users);

        // then
        assertThat(women.size()).isEqualTo(2);
    }

    @Test
    public void shouldReturn0ForWomenCollectionSize() {
        // given
        users.add(new User(1L, "Patryk", 25));
        users.add(new User(2L, "Dominik", 15));

        // when
        Collection<User> women = streamsTask.findWomen(users);

        // then
        assertThat(women.size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnAvgAge10For0And20() {
        // given
        users.add(new User(1L, "Patryk", 0));
        users.add(new User(2L, "Dominik", 20));

        // when
        Double averageMenAge = streamsTask.averageMenAge(users);

        // then
        assertThat(averageMenAge).isEqualTo(10);
    }

    @Test
    public void shouldReturnAvgAge0ForTwo0() {
        // given
        users.add(new User(1L, "Patryk", 0));
        users.add(new User(2L, "Dominik", 0));

        // when
        Double averageMenAge = streamsTask.averageMenAge(users);

        // then
        assertThat(averageMenAge).isEqualTo(0);
    }

    @Test
    public void shouldReturnAvgAge10For5And10And15() {
        // given
        users.add(new User(1L, "Patryk", 5));
        users.add(new User(2L, "Dominik", 10));
        users.add(new User(3L, "Ryszard", 15));

        // when
        Double averageMenAge = streamsTask.averageMenAge(users);

        // then
        assertThat(averageMenAge).isEqualTo(10);
    }

    @Test
    public void shouldReturn3For3Users() {
        // given
        users.add(new User(1L, "Patryk", 51));
        users.add(new User(2L, "Dominik", 12));
        users.add(new User(3L, "Ryszard", 6));

        expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
        expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
        expenses.add(new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));
        expenses.add(new Expense(3L, "Skarpetki", new BigDecimal("39"), ExpenseType.WEAR));
        expenses.add(new Expense(3L, "Pizza", new BigDecimal("25"), ExpenseType.FOOD));

        // when
        Map<Long, List<Expense>> expensesByUserId = streamsTask.groupExpensesByUserId(users, expenses);

        // then
        assertThat(expensesByUserId.size()).isEqualTo(3);
    }

    @Test
    public void shouldReturnTrueFor2Users() {
        // given
        users.add(new User(1L, "Patryk", 51));
        users.add(new User(2L, "Dominik", 12));

        expenses.add(new Expense(2L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
        expenses.add(new Expense(2L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
        expenses.add(new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));

        // when
        Map<Long, List<Expense>> expensesByUserId = streamsTask.groupExpensesByUserId(users, expenses);

        // then
        assertThat(expensesByUserId.containsKey(2L)).isTrue();
    }

    @Test
    public void shouldReturn0For2UsersWithoutExpenses() {
        // given
        users.add(new User(1L, "Patryk", 51));
        users.add(new User(2L, "Ryszard", 6));

        // when
        Map<Long, List<Expense>> expensesByUserId = streamsTask.groupExpensesByUserId(users, expenses);

        // then
        assertThat(expensesByUserId.size()).isEqualTo(0);
    }

    @Test
    public void shouldReturn1For2UsersOneWithExpenses() {
        // given
        users.add(new User(1L, "Patryk", 51));
        users.add(new User(2L, "Dominik", 12));

        expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
        expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
        expenses.add(new Expense(1L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));
        expenses.add(new Expense(1L, "Skarpetki", new BigDecimal("39"), ExpenseType.WEAR));
        expenses.add(new Expense(1L, "Pizza", new BigDecimal("25"), ExpenseType.FOOD));

        // when
        Map<Long, List<Expense>> expensesByUserId = streamsTask.groupExpensesByUserId(users, expenses);

        // then
        assertThat(expensesByUserId.size()).isEqualTo(1);
    }

    @Test
    public void shouldReturn2For2Users() {
        // given
        users.add(new User(1L, "Patryk", 51));
        users.add(new User(2L, "Dominik", 12));

        expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
        expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
        expenses.add(new Expense(1L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));
        expenses.add(new Expense(2L, "Skarpetki", new BigDecimal("39"), ExpenseType.WEAR));
        expenses.add(new Expense(2L, "Pizza", new BigDecimal("25"), ExpenseType.FOOD));

        // when
        Map<User, List<Expense>> expensesByUser = streamsTask.groupExpensesByUser(users, expenses);

        // then
        assertThat(expensesByUser.size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnTrueFor2UsersWithExpenses() {
        // given
        User user1 = new User(1L, "Patryk", 51);
        User user2 = new User(2L, "Dominik", 12);
        users.add(user1);
        users.add(user2);

        expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
        expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
        expenses.add(new Expense(2L, "Skarpetki", new BigDecimal("39"), ExpenseType.WEAR));
        expenses.add(new Expense(2L, "Pizza", new BigDecimal("25"), ExpenseType.FOOD));

        // when
        Map<User, List<Expense>> expensesByUser = streamsTask.groupExpensesByUser(users, expenses);

        // then
        assertThat(expensesByUser.containsKey(user1)).isTrue();
        assertThat(expensesByUser.containsKey(user2)).isTrue();
    }

    @Test
    public void shouldReturn1For2UsersOneWithNoExpenses() {
        // given
        users.add(new User(1L, "Patryk", 51));
        users.add(new User(2L, "Dominik", 12));

        expenses.add(new Expense(2L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
        expenses.add(new Expense(2L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
        expenses.add(new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));
        expenses.add(new Expense(2L, "Skarpetki", new BigDecimal("39"), ExpenseType.WEAR));
        expenses.add(new Expense(2L, "Pizza", new BigDecimal("25"), ExpenseType.FOOD));

        // when
        Map<User, List<Expense>> expensesByUser = streamsTask.groupExpensesByUser(users, expenses);

        // then
        assertThat(expensesByUser.size()).isEqualTo(1);
    }

    @Test
    public void shouldBeEmptyFor2UsersWithNoExpenses() {
        // given
        users.add(new User(1L, "Patryk", 51));
        users.add(new User(2L, "Dominik", 12));

        // when
        Map<User, List<Expense>> expensesByUser = streamsTask.groupExpensesByUser(users, expenses);

        // then
        assertThat(expensesByUser).isEmpty();
    }

}
