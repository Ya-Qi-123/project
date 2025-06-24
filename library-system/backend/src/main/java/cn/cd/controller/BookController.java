package cn.cd.controller;

import cn.cd.domain.TBook;
import cn.cd.service.BookService;
import cn.cd.util.AjaxResult;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    private static final int MAX_INTEGER_DIGITS = 8;
    private static final int MAX_DECIMAL_DIGITS = 2;

    @GetMapping("/pageQueryForAdmin")
    public AjaxResult getBooksByPageForAdmin(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn) {

        Page<TBook> page = bookService.getBooksByPageForAdmin(currentPage, size, name, author, isbn);
        return AjaxResult.ok().setData(page);
    }

    @GetMapping("/pageQueryForUser")
    public AjaxResult getBooksByPageForUser(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String category) {

        Page<TBook> page = bookService.getBooksByPageForUser(currentPage, size, name, author, isbn, category);
        return AjaxResult.ok().setData(page);
    }

    @PostMapping("/add")
    public AjaxResult addBook(
            @RequestParam String isbn,
            @RequestParam String name,
            @RequestParam String author,
            @RequestParam BigDecimal price,
            @RequestParam String publisher,
            @RequestParam String category) {
        String errorMsg = validateBookFields(isbn, name, price,
                author, publisher, category);
        if (errorMsg != null) {
            return AjaxResult.fail(errorMsg);
        }
        bookService.addBook(isbn, name, price, author, publisher, category);
        return AjaxResult.me().setMessage("添加成功");

    }

    @GetMapping("/list")
    public AjaxResult getByBookList() {
        List<TBook> books = bookService.HomePageService();
        return books != null && !books.isEmpty()
                ? AjaxResult.ok(books)
                : AjaxResult.ok(Collections.emptyList());
    }

    @PostMapping("/update")
    public AjaxResult updateBook(TBook  book) {
        if (!isValidId(book.getId())) {
            return AjaxResult.fail("图书ID格式错误");
        }
        // 2. 检查图书是否存在
        if (bookService.getById(book.getId()) == null) {
            return AjaxResult.fail("图书ID不存在，无法更新");
        }
        String errorMsg = validateBookFields(book.getIsbn(), book.getName(),
                book.getPrice(), book.getAuthor(),
                book.getPublisher(), book.getCategory());
        if (errorMsg != null) {
            return AjaxResult.fail(errorMsg);
        }
        bookService.updateBook(book);
        return AjaxResult.me().setMessage("更新成功");
    }

    @PostMapping("updateStatus")
    public AjaxResult updateStatus(Long id, Integer status) {
        bookService.updateBookStatus(id, status);
        return AjaxResult.me().setMessage("更新成功");
    }

    @PostMapping("/batchDelete")
    public AjaxResult batchDeleteBooks(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return AjaxResult.fail("请选择要删除的图书");
        }
        int result = bookService.batchDeleteBooks(ids);
        return result > 0
                ? AjaxResult.ok("成功删除" + result + "本图书")
                : AjaxResult.fail("删除失败");
    }

    @GetMapping("/getById")
    public AjaxResult getByBookDetail(@RequestParam Long id) {
        if (!isValidId(id)) {
            return AjaxResult.fail("图书ID格式错误");
        }
        TBook book = bookService.getBookById(id);
        return book != null
                ? AjaxResult.ok(book)
                : AjaxResult.fail("图书不存在");
    }

    private boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    private String validateBookFields(String isbn, String name,
                                      BigDecimal price, String author,
                                      String publisher, String category) {
        if (StringUtils.isEmpty(isbn)) return "ISBN不能为空";
        if (StringUtils.isEmpty(name)) return "图书名称不能为空";
        if (StringUtils.isEmpty(author)) return "作者不能为空";
        if (StringUtils.isEmpty(author)) return "出版社不能为空";
        if (StringUtils.isEmpty(publisher)) return "状态不能为空";
        if (StringUtils.isEmpty(category)) return "分类不能为空";
        if (price == null) return "价格不能为空";

        // 价格校验
        return validatePrice(price);
    }

    private String validatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return "价格格式错误";
        }
        //价格的整数部分和小数部分长度不能超过8位和2位
        int integerDigits = price.precision() - price.scale();
        int decimalDigits = Math.max(0, price.scale());

        if (integerDigits > MAX_INTEGER_DIGITS) {
            return String.format("价格整数部分最多%d位", MAX_INTEGER_DIGITS);
        }
        if (decimalDigits > MAX_DECIMAL_DIGITS) {
            return String.format("价格小数部分最多%d位", MAX_DECIMAL_DIGITS);
        }
        return null;
    }


}


