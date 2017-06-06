package com.excilys.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={com.excilys.cdb.config.DataConfig.class})
public class CompanyDAOTest {    
    @Autowired
	private CompanyDAO companyDao;
    
    @Before
    public void init() throws Exception{
        Properties prop = new Properties();
        prop.load(new FileInputStream(new File("src/test/resources/local.properties")));
        
        IDataSet dataset = new FlatXmlDataSetBuilder().build(new File("src/test/resources/databaseCom.xml"));
        IDatabaseTester databaseTester = new JdbcDatabaseTester(
                prop.getProperty("db.driver"),
                prop.getProperty("db.url"),
                prop.getProperty("db.username"),
                prop.getProperty("db.password"));
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataset);
        databaseTester.onSetup();
    }
    
    @Test
    public void createCompany() {
        Company com = new Company.Builder().name("Bob Inc.").build();
        Company com2;
        
        assertEquals(0L, com.getId());
        com2 = companyDao.create(com);
        assertNotEquals(0L, com2.getId());
    }

    @Test
    public void companyDelete() {
    	assertNotNull(companyDao.find(1L));
    	companyDao.delete(1L);
    	assertNull(companyDao.find(1L));
    }
    
    @Test
    public void companyFindAll() {
        Company com = new Company.Builder().id(1L).name("Bob Inc.").build();
        List<Company> companies = new ArrayList<>();
        companies.add(com);
        
        assertEquals(companies, companyDao.findAll());
    }
    
    @Test
    public void companyFindById() {
        Company com = new Company.Builder().id(1L).name("Bob Inc.").build();
        
        assertEquals(com, companyDao.find(1L));
    }
    
    @Test
    public void companyUpdate() {
        Company com1 = new Company.Builder().id(1L).name("Bob Inc.").build();
        Company com2 = new Company.Builder().id(1L).name("Bob Inc. 2").build();
        
        assertNotEquals(com1, com2);
        assertEquals(com1, companyDao.find(1L));
        companyDao.update(com2);
        assertEquals(com2, companyDao.find(1L));
    }
}
