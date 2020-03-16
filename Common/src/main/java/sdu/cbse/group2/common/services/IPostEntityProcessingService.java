package sdu.cbse.group2.common.services;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService  {
        void process(GameData gameData, World world);
}
