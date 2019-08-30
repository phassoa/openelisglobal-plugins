/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations under
 * the License.
 *
 * The Original Code is OpenELIS code.
 *
 * Copyright (C) ITECH, University of Washington, Seattle WA.  All Rights Reserved.
 */

package oe.plugin.analyzer;

import org.openelisglobal.common.services.PluginMenuService;
import org.openelisglobal.common.services.PluginMenuService.KnownMenu;
import org.openelisglobal.common.util.ConfigurationProperties;
import org.openelisglobal.menu.util.MenuUtil;
import org.openelisglobal.menu.valueholder.Menu;
import org.openelisglobal.plugin.MenuPlugin;

public class WeberMenu extends MenuPlugin {

	@Override
	protected void insertMenu() {
        PluginMenuService service = PluginMenuService.getInstance();
        Menu menu = new Menu();
		
		menu.setParent(PluginMenuService.getInstance().getKnownMenu(KnownMenu.ANALYZER, "menu_results"));
		menu.setPresentationOrder(5);
		menu.setElementId("weber_analyzer_plugin");
		menu.setActionURL("/AnalyzerResults.do?type=WeberAnalyzer");
		menu.setDisplayKey("banner.menu.results.weber");
		menu.setOpenInNewWindow(false);

        service.addMenu(menu);
        service.insertLanguageKeyValue("banner.menu.results.weber","Weber 4000", ConfigurationProperties.LOCALE.ENGLISH.getRepresentation());
        service.insertLanguageKeyValue("banner.menu.results.weber","La Weber 4000", ConfigurationProperties.LOCALE.FRENCH.getRepresentation());
	}
	
}
