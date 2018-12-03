package com.example.demo.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
	
	@Select("select b.bookid, b.title, b.publisher, t1.numberavailable\r\n" + 
			"from book b,(select bc.bookid, bc.branchid,bc.no_of_copies numberavailable\r\n" + 
			"             from book_copies bc\r\n" + 
			"             where bc.branchid = #{branchid}) t1\r\n" + 
			"where b.bookid = t1.bookid")
	List<Book> getBookListByBranch(LibraryBranch branch);
}
