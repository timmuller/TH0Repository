package husacct.graphics.task.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jhotdraw.draw.Figure;

public class NodeList implements Collection<Node>, Iterable<Node> {
	private ArrayList<Node> nodes;
	
	public NodeList() {
		nodes = new ArrayList<Node>();
	}
	
	public boolean add(Node n) {
		return nodes.add(n);
	}
	
	@Override
	public boolean contains(Object o) {
		for (Node node : nodes) {
			if (node.equals(o))
				return true;
		}
		
		return false;
	}
	
	public Node getByFigure(Figure f) {
		for (Node n : nodes) {
			if (n.equals(f))
				return n;
		}
		
		// TODO: Patrick: Should this throw an exception or return null?
		// Please check the Java API documentation concerning collections to see how 
		// the Java API solves this.
		return null;
	}

	@Override
	public Iterator<Node> iterator() {
		return nodes.iterator();
	}
	
	public void clear() {
		nodes.clear();
	}

	@Override
	public boolean addAll(Collection<? extends Node> arg0) {
		return nodes.addAll(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return nodes.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return nodes.isEmpty();
	}

	@Override
	public boolean remove(Object arg0) {
		return nodes.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return nodes.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return nodes.retainAll(arg0);
	}

	@Override
	public int size() {
		return nodes.size();
	}

	@Override
	public Object[] toArray() {
		return nodes.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return nodes.toArray(arg0);
	}
	
}