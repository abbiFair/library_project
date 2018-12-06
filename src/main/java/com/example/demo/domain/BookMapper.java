package com.example.demo.domain;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookMapper {
	@Select("select b.bookid, b.title, b.publisher, t4.numberavailable\r\n" + 
			"from book b, (select bid, (sum3 - fixed_count) numberavailable\r\n" + 
			"from (select bid, sum3, coalesce(total, 0) fixed_count from\r\n" + 
			"(select t1.bookid bid, t1.sum1 sum3, t2.count1 total\r\n" + 
			"from(select bc.bookid, sum(bc.no_of_copies) sum1\r\n" + 
			"     from book_copies bc\r\n" + 
			"     group by bc.bookid) t1 left join (select bl.bookid, count(bl.bookid) count1\r\n" + 
			"                                    from book_loans bl\r\n" + 
			"                                    where bl.datein is null \r\n" + 
			"                                    group by bl.bookid) t2 on t1.bookid = t2.bookid))) t4\r\n" + 
			"where b.bookid = t4.bid")
	List<Book> getBookList();
	
	@Select("select bookid, title, publisher from book where bookid = #{bookid}")
	Book getBook(Book book);
	
//	@Select("select b.bookid, b.title, b.publisher, t1.numberavailable\r\n" + 
//			"from book b,(select bc.bookid, bc.branchid,bc.no_of_copies numberavailable\r\n" + 
//			"             from book_copies bc\r\n" + 
//			"             where bc.branchid = #{branchid}) t1\r\n" + 
//			"where b.bookid = t1.bookid")
//	List<Book> getBookListByBranch(LibraryBranch branch);
	
	@Select("select t2.bookid, t2.title, t2.publisher, (t2.navailable - coalesce(t5.count1,0)) numberavailable, t5.average rating\r\n" + 
			"from\r\n" + 
			"(select b.bookid, b.title, b.publisher, t1.available navailable \r\n" + 
			"from book b,(select bc.bookid, bc.branchid,bc.no_of_copies available\r\n" + 
			"			 from book_copies bc\r\n" + 
			"             where bc.branchid = #{branchid}) t1\r\n" + 
			"where b.bookid = t1.bookid) t2\r\n" + 
			"left join\r\n" + 
			"(select t3.bookid, t3.count1, t4.average\r\n" + 
			"from (select bl.bookid, count(bl.bookid) count1, avg(bl.rating)\r\n" + 
			"from book_loans bl\r\n" + 
			"where bl.datein is null and bl.branchid = #{branchid} \r\n" + 
			"group by bl.bookid) t3,\r\n" + 
			"(select bl.bookid, avg(bl.rating) average\r\n" + 
			"from book_loans bl\r\n" + 
			"where bl.branchid = #{branchid} \r\n" + 
			"group by bl.bookid) t4\r\n" + 
			"where t3.bookid = t4.bookid) t5 on t2.bookid = t5.bookid")
	List<Book> getBookListByBranch(LibraryBranch branch);

	@Insert("insert into book_loans values (${bookid},${branchid},${cardno},sysdate,add_months(sysdate,1),null, null)")
	void insertBookLoan(BookLoan bookloan);

	@Select("select b1.bookid, b1.branchid, b1.cardno, b1.duedate, b2.title from book_loans b1, book b2 "
			+ "where b1.cardno = #{cardno} and b1.bookid = b2.bookid")
	List<BookLoan> getBookLoanByCard(String cardno);

	
	@Update("UPDATE BOOK_LOANS set rating = #{rating} WHERE cardno = #{cardno}, bookid = #{bookid}, branchid=#{branchid}")
	void updateBookLoan(BookLoan bookloan);
}
