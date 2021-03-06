package com.bird.statemachine.scene;

import com.bird.statemachine.StateContext;
import com.bird.statemachine.StateProcessor;

/**
 * @author liuxx
 * @since 2021/5/11
 */
public interface SceneStateProcessor <C extends StateContext> extends StateProcessor<C> {

    /**
     * scene
     *
     * @return scene id
     */
    default String getSceneId() {
        return StateContext.DEFAULT_SCENE;
    }
}
