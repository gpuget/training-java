package com.excilys.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
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
import com.excilys.cdb.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { com.excilys.cdb.config.DataConfig.class })
public class ComputerDAOTest {
    @Autowired
    private ComputerDAO computerDao;

    Company com1;
    Company com2;

    Computer cpu1;
    Computer cpu2;
    Computer cpu3;

    @Before
    public void init() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream(new File("src/test/resources/local.properties")));

        IDataSet dataset = new FlatXmlDataSetBuilder()
                .build(new File("src/test/resources/databaseCpu.xml"));
        IDatabaseTester databaseTester = new JdbcDatabaseTester(prop.getProperty("db.driver"),
                prop.getProperty("db.url"), prop.getProperty("db.username"),
                prop.getProperty("db.password"));
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataset);
        databaseTester.onSetup();

        com1 = new Company.Builder().id(1L).name("Bob Inc.").build();
        com2 = new Company.Builder().id(2L).name("Bob Inc2.").build();

        cpu1 = new Computer.Builder().id(1L).name("Alice").introduced(LocalDate.of(1993, 01, 01))
                .discontinued(LocalDate.of(2017, 01, 01)).manufacturer(com1).build();
        cpu2 = new Computer.Builder().id(2L).name("Bob").introduced(LocalDate.of(1993, 01, 02))
                .discontinued(LocalDate.of(2017, 01, 02)).manufacturer(com2).build();
        cpu3 = new Computer.Builder().id(3L).name("Ivan").introduced(LocalDate.of(1993, 01, 03))
                .discontinued(LocalDate.of(2017, 01, 03)).manufacturer(com2).build();
    }

    @Test
    public void createComputer() {
        cpu1.setId(0L);
        cpu2 = computerDao.create(cpu1);
        assertNotEquals(0L, cpu2.getId());
    }

    @Test
    public void computerDelete() {
        assertNotNull(computerDao.find(1L));
        computerDao.delete(1L);
        assertNull(computerDao.find(1L));
    }

    @Test
    public void computerDeleteMany() {
        assertNotNull(computerDao.find(1L));
        assertNotNull(computerDao.find(2L));

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        computerDao.delete(ids);
        assertNull(computerDao.find(1L));
        assertNull(computerDao.find(2L));
    }

    @Test
    public void computerDeleteFromCompany() {
        assertNotNull(computerDao.find(1L));
        assertNotNull(computerDao.find(2L));

        computerDao.deleteFromCompany(1L);
        assertNull(computerDao.find(1L));
        assertNotNull(computerDao.find(2L));

    }

    @Test
    public void computerFindAll() {
        List<Computer> computers = new ArrayList<>();
        computers.add(cpu1);
        computers.add(cpu2);
        computers.add(cpu3);

        assertEquals(computers, computerDao.findAll());
    }

    @Test
    public void computerFindAllWithLimit() {
        assertEquals(1, computerDao.findAll(1, 0, "id", 0).size());
    }

    @Test
    public void computerFindAllWithOffset() {
        assertEquals(2, computerDao.findAll(3, 1, "id", 0).size());
    }

    @Test
    public void computerFindAllWithSortId() {
        List<Computer> computers = new ArrayList<>();

        computers.add(cpu1);
        assertEquals(computers, computerDao.findAll(1, 0, "id", 1));

        computers.remove(0);
        computers.add(cpu3);
        assertEquals(computers, computerDao.findAll(1, 0, "id", 0));
    }

    @Test
    public void computerFindAllWithSortName() {
        List<Computer> computers = new ArrayList<>();

        computers.add(cpu1);
        assertEquals(computers, computerDao.findAll(1, 0, "name", 1));

        computers.remove(0);
        computers.add(cpu3);
        assertEquals(computers, computerDao.findAll(1, 0, "name", 0));
    }

    @Test
    public void computerFindAllWithSortIntroduced() {
        List<Computer> computers = new ArrayList<>();

        computers.add(cpu1);
        assertEquals(computers, computerDao.findAll(1, 0, "name", 1));

        computers.remove(0);
        computers.add(cpu3);
        assertEquals(computers, computerDao.findAll(1, 0, "name", 0));
    }

    @Test
    public void computerFindAllWithSortDiscontinued() {
        List<Computer> computers = new ArrayList<>();

        computers.add(cpu1);
        assertEquals(computers, computerDao.findAll(1, 0, "discontinued", 1));

        computers.remove(0);
        computers.add(cpu3);
        assertEquals(computers, computerDao.findAll(1, 0, "discontinued", 0));
    }

    @Test
    public void computerFindAllWithSortCompanyName() {
        List<Computer> computers = new ArrayList<>();

        computers.add(cpu1);
        assertEquals(computers, computerDao.findAll(1, 0, "companyName", 1));

        computers.remove(0);
        computers.add(cpu2);
        assertEquals(computers, computerDao.findAll(1, 0, "companyName", 0));
    }

    @Test
    public void computerFindById() {
        assertEquals(cpu1, computerDao.find(1L));
    }

    @Test
    public void computerFindByName() {
        List<Computer> computers = new ArrayList<>();

        computers.add(cpu1);
        assertEquals(computers, computerDao.findByName(1, 0, "id", 1, "Alice"));
    }

    @Test
    public void computerCount() {
        assertEquals(3, computerDao.getCount());
        assertEquals(1, computerDao.getCount("Alice"));
    }

    @Test
    public void computerUpdate() {
        cpu2.setId(1L);
        assertNotEquals(cpu1, cpu2);
        assertEquals(cpu1, computerDao.find(1L));
        computerDao.update(cpu2);
        assertEquals(cpu2, computerDao.find(1L));
    }
}
