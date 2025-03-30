package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@SpringBootApplication
@RestController
public class BankingSystemApplication implements CommandLineRunner{
	
	private static JdbcTemplate jdbcTemplate;
	
	private BankingSystemApplication(JdbcTemplate template)
	{
		this.jdbcTemplate=template;
	}
	
    public static void main(String[] args) {
      SpringApplication.run(BankingSystemApplication.class, args);
    }
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }
	@Override
	public void run(String... args) throws Exception {
		String sql = "SELECT * FROM TELLER";
		
		List<Teller> tellers = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Teller.class));
		tellers.forEach(System.out :: println);
	}
}