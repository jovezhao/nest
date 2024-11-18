package com.zhaofujun.nest.ddd;
/**
 * Represents the abstract class for an aggregate root, which is a pattern used in domain-driven design (DDD).
 * An aggregate root is a special entity that acts as the entry point to a group of related objects, which together form an aggregate.
 * This abstract class primarily defines a generic type for the identifier of the aggregate root.
 *
 * @param <T> The type of the identifier, which must extend the Identifier class.
 */
public abstract class AggregateRoot<T extends Identifier> extends Entity<T> {

    /**
     * Constructs an instance of the aggregate root.
     *
     * @param id The identifier for the aggregate root, which cannot be null.
     */
    public AggregateRoot(T id) {
        super(id);
    }
}