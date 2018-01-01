package com.spring.dao;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.persistence.ValidationMode;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;


import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.model.Employee;

@Component
/*@Resource
@Qualifier("empDao")
@Transactional()*/
public class EmployeeDao {
	
	@Autowired
	SessionFactory sessionFactory;

	/*public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	*/ @Transactional()
	public int addEmployee(Employee empBean) throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException{
		/*Session session= sessionFactory.getCurrentSession();*/
		Session session = sessionFactory.openSession();
		  Transaction tx = session.beginTransaction();
		/*Session session= sessionFactory.getCurrentSession();*/
	    int  i = (Integer) session.save(empBean);
		/*Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "INSERT INTO Employee(firstName, lastName, address) values(?,?,?)"  + 
	             "SELECT firstName, lastName,address FROM employee400";
		
		session.save(empBean.getFirstname());
		session.save(empBean.getLastname());
		session.save(empBean.get);
	Query query = session.createQuery(hql);
	query.setParameter(1, empBean.getLastname());
	query.setParameter(2, empBean.getLastname());
	query.setParameter(3, empBean.getAddress());
	int result = query.executeUpdate();
	System.out.println("Rows affected: " + result);*/
	    tx.commit();
	    session.close();
	    System.out.println("employee saved successfully, Person Details="+empBean);
	    System.out.println("emp id is "+i);
		return i;
		}
	 @Transactional()
	public Employee updateEmployee(Employee empBean) throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException{
		Session session= sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
	    Employee em = (Employee) session.merge(empBean);
	    tx.commit();
	    session.close();
	    System.out.println("Employee update successfully, Person Details="+empBean);
		return em;
		}
	 @Transactional()
	 @SuppressWarnings("unchecked")
	public ArrayList<Employee> listemployee(){
		ArrayList<Employee> empList = new ArrayList<Employee>();
		 /*for(Employee empBean : empList){
	            logger.info("Person List::"+p);
	        }*/
		Session session= sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "FROM Employee WHERE dflag = :dflag";
		Query query = session.createQuery(hql);
		query.setParameter("dflag",1);
		return (ArrayList<Employee>) query.list();
		/*return (ArrayList<Employee>) sessionFactory.openSession().createQuery("SELECT E.* FROM Employee E WHERE E.dflag =: 1 ")
			    .list();*/
		/*return (ArrayList<Employee>) sessionFactory.openSession().createQuery("select id, firstname , lastname , address from Employee where dflag = 1")
			    .list();*/
		}
	 @Transactional()
	public Employee getById(int id){
		return (Employee) sessionFactory.openSession().get(Employee.class, id);
		}
	 @Transactional()
	public Integer deleteEmp(int id) throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException{
		 System.out.println("before id :"+id +"dflag"+((Employee) sessionFactory.openSession().get(Employee.class, id)).getDflag());
	 	 /*Employee emp  = new Employee();
	 	 emp.setDflag(0);
	 	 emp.setId(id);*/
		 Session session= sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "Update Employee set dflag = :dflag where id = "+id;
		Query query = session.createQuery(hql);
		query.setParameter("dflag",0);
		int i = query.executeUpdate();
		System.out.println("i is :::"+i);
		/*session.createQuery("update Employee E set E.dfalg = 0 where E.id ="+id).executeUpdate();*/
		//String hql = "update Employee set dfalg = 1 where id ="+ id ;
	             
		//Query query = session.createQuery(hql);
		//query.executeUpdate();
		tx.commit();
		System.out.println("after id:"+id+" dflag = "+((Employee) sessionFactory.openSession().get(Employee.class, id)).getDflag());
		if(i>0){
			i = 1;
		}else{
			i=0;
		}
		  session.close();
		return i;
	}
	/* @Transactional()
	 @SuppressWarnings("unchecked")
	public ArrayList<Employee> findBySkill(Employee emp) {
		ArrayList<Employee> empList = new ArrayList<Employee>();
		 for(Employee empBean : empList){
	            logger.info("Person List::"+p);
	        }
		Session session= sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "FROM Employee WHERE skill = :skill";
		Query query = session.createQuery(hql);
		query.setParameter("skill",emp.getSkill());
		return (ArrayList<Employee>) query.list();
		
		
	}*/

}
