package com.money.moneytrack_be.seed;

import com.money.moneytrack_be.entity.Category;
import com.money.moneytrack_be.enums.CategoryType;
import com.money.moneytrack_be.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDataSeeder implements ApplicationRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (categoryRepository.count() > 0) {
            return;
        }

        seedIncome();
        seedExpense();
        seedDebt();
    }

    private void seedIncome() {
        Category salary     = saveParent("Salary",        CategoryType.INCOME);
        Category freelance  = saveParent("Freelance",     CategoryType.INCOME);
        Category investment = saveParent("Investment",    CategoryType.INCOME);
        Category other      = saveParent("Other Income",  CategoryType.INCOME);

        saveChildren(salary,     List.of("Monthly Salary", "Bonus"),                CategoryType.INCOME);
        saveChildren(freelance,  List.of("Project Payment", "Consulting"),          CategoryType.INCOME);
        saveChildren(investment, List.of("Dividends", "Interest", "Capital Gains"), CategoryType.INCOME);
        saveChildren(other,      List.of("Gift", "Refund"),                         CategoryType.INCOME);
    }

    private void seedExpense() {
        Category food          = saveParent("Food & Drink",    CategoryType.EXPENSE);
        Category transport     = saveParent("Transport",       CategoryType.EXPENSE);
        Category housing       = saveParent("Housing",         CategoryType.EXPENSE);
        Category healthcare    = saveParent("Healthcare",      CategoryType.EXPENSE);
        Category entertainment = saveParent("Entertainment",   CategoryType.EXPENSE);
        Category shopping      = saveParent("Shopping",        CategoryType.EXPENSE);
        Category education     = saveParent("Education",       CategoryType.EXPENSE);
        Category other         = saveParent("Other Expense",   CategoryType.EXPENSE);

        saveChildren(food,          List.of("Restaurant", "Groceries", "Coffee"),          CategoryType.EXPENSE);
        saveChildren(transport,     List.of("Fuel", "Public Transit", "Taxi"),             CategoryType.EXPENSE);
        saveChildren(housing,       List.of("Rent", "Utilities", "Maintenance"),           CategoryType.EXPENSE);
        saveChildren(healthcare,    List.of("Medicine", "Doctor", "Insurance"),            CategoryType.EXPENSE);
        saveChildren(entertainment, List.of("Movies", "Games", "Streaming"),               CategoryType.EXPENSE);
        saveChildren(shopping,      List.of("Clothing", "Electronics", "Home Goods"),      CategoryType.EXPENSE);
        saveChildren(education,     List.of("Tuition", "Books", "Online Courses"),         CategoryType.EXPENSE);
        saveChildren(other,         List.of("Miscellaneous"),                              CategoryType.EXPENSE);
    }

    private void seedDebt() {
        Category loan       = saveParent("Loan Payment",  CategoryType.DEBT);
        Category creditCard = saveParent("Credit Card",   CategoryType.DEBT);
        Category other      = saveParent("Other Debt",    CategoryType.DEBT);

        saveChildren(loan,       List.of("Personal Loan", "Car Loan", "Mortgage"), CategoryType.DEBT);
        saveChildren(creditCard, List.of("Monthly Payment", "Minimum Payment"),    CategoryType.DEBT);
        saveChildren(other,      List.of("Family Loan", "Friend Loan"),            CategoryType.DEBT);
    }

    private Category saveParent(String name, CategoryType type) {
        return categoryRepository.save(
                Category.builder().name(name).type(type).build()
        );
    }

    private void saveChildren(Category parent, List<String> names, CategoryType type) {
        names.forEach(name ->
                categoryRepository.save(
                        Category.builder().name(name).type(type).parent(parent).build()
                )
        );
    }
}
