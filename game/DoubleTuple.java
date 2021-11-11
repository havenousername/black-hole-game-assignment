/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package assignmenttwoblackhole.game;
import java.util.Objects;

public class DoubleTuple<T> {
    T first;
    T last;
    
    public DoubleTuple(T first, T last) {
        this.first = first;
        this.last = last;
        
        
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DoubleTuple<?> other = (DoubleTuple<?>) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.last, other.last)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "DoubleTuple{" + "first=" + first.toString() + ", last=" + last.toString() + '}';
    }
    
   
    public boolean equals(int first, int last) {
        return Objects.equals(this.last, last) && Objects.equals(this.first, first);
    }
    
    public T getFirst() {
        return first;
    }

    public T getLast() {
        return last;
    }
}

