package cn.hofan.spat.mvc2.repository;

import java.util.List;

public  interface Repository<T> {
	T findById();
	boolean add(T t);
	boolean save(T t);
	
	List<T> list(int start, int length);
	
	boolean delete(T t);
	boolean delete(int id);
	
	// other
	
}
