package me.mikex86.scalargrad.nn;

import me.mikex86.scalargrad.Value;

import java.util.List;

public interface Module {

    List<Value> getParameters();

}
