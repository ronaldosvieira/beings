package entity.model;

import entity.model.strategies.MoveStrategy;

public interface Moveable {
    public MoveStrategy getMoveStrategy();
    public void setMoveStrategy(MoveStrategy strat);
}
