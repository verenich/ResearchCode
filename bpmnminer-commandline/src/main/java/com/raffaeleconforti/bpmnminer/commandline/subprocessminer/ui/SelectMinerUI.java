/*
 *  Copyright (C) 2018 Raffaele Conforti (www.raffaeleconforti.com)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.raffaeleconforti.bpmnminer.commandline.subprocessminer.ui;

import com.raffaeleconforti.bpmnminer.subprocessminer.selection.SelectMinerResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raffaele Conforti on 20/02/14.
 */

public class SelectMinerUI {

    public SelectMinerResult showGUI() {

        List<String> allAttributes = new ArrayList<String>();
        allAttributes.add(SelectMinerResult.HM5);
        allAttributes.add(SelectMinerResult.IM);
        allAttributes.add(SelectMinerResult.ALPHA);
        allAttributes.add(SelectMinerResult.ILP);
        allAttributes.add(SelectMinerResult.SM);
        allAttributes.add(SelectMinerResult.HM6);

        // show ui to user to confirm/select primary keys
        SelectMiner ignoreGui = new SelectMiner(allAttributes);

        return ignoreGui.getSelectedAlgorithm();

    }

}
