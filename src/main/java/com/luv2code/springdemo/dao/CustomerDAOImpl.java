package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	// inject page size from props	
	@Value("${pageSize:10}")
	private int pageSize;
	
	@Override
	public List<Customer> getCustomers() {
		
		//get the current Hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query ... sort by last name
		Query<Customer> theQuery = 
				currentSession.createQuery("FROM Customer ORDER BY lastName", 
										    Customer.class);
		
		//execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		
		//return the list of customers		
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession(); 
		
		//save/update the customer
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession(); 
		
		//now retrieve/read the object from the database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession(); 
				
		//delete the object using primary key
		Query theQuery = 
				currentSession.createQuery("DELETE FROM Customer WHERE id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}

	@Override
    public List<Customer> searchCustomers(String theSearchName) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        
        Query<Customer> theQuery = null;
        
        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {

            // search for firstName or lastName ... case insensitive
            theQuery = 
            		currentSession.createQuery(
            				"from Customer where lower(firstName) like :theName or lower(lastName) like :theName"
            				, Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

        }
        else {
            // theSearchName is empty, return null for higher level redirection
            return null;            
        }
        
        // execute query and get result list                
        // return the results        
        return theQuery.getResultList();
        
    }
	
	@Override
	public List<Customer> getCustomersByPage(int pageNumber) {
		
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Customer> query = currentSession.createQuery("From Customer order by lastName", Customer.class);
		query.setFirstResult((pageNumber-1) * pageSize);
		query.setMaxResults(pageSize);
		
		List<Customer> customers = query.getResultList();
		
		return customers;
	}

	@Override
	public long getCustomersCount() {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Long> countQuery = currentSession.createQuery("select count(1) from Customer", Long.class);
		long count = countQuery.getSingleResult().longValue();
		
		return count;
	}
	
}
