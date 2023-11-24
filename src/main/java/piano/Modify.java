package piano;

/**
* Defines an lambda expression which when invoked alters the 
* value of an instance variable in a fixed way.
*/
@FunctionalInterface
interface Modify {
    public abstract void apply();
}