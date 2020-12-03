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

import us.mn.state.health.lims.common.services.PluginMenuService;
import us.mn.state.health.lims.common.util.ConfigurationProperties;
import us.mn.state.health.lims.menu.valueholder.Menu;
import us.mn.state.health.lims.plugin.MenuPlugin;

public class TaqMan96VLMenu extends MenuPlugin
{
  protected void insertMenu()
  {
    PluginMenuService service = PluginMenuService.getInstance();
    Menu menu = new Menu();

    menu.setParent(PluginMenuService.getInstance().getKnownMenu(PluginMenuService.KnownMenu.ANALYZER, "menu_results"));

    menu.setPresentationOrder(32);

    menu.setElementId("taqman96_vl_analyzer_plugin2");

    menu.setActionURL("/AnalyzerResults.do?type=TaqMan96VLAnalyzer2");
    menu.setDisplayKey("banner.menu.results.taqman96vlanalyzer2");
    menu.setOpenInNewWindow(false);

    service.addMenu(menu);

    service.insertLanguageKeyValue("banner.menu.results.taqman96vlanalyzer2", "Virology: Cobas TaqMan96-2: Viral Load", ConfigurationProperties.LOCALE.ENGLISH.getRepresentation());

    service.insertLanguageKeyValue("banner.menu.results.taqman96vlanalyzer2", "Virologie: Cobas TaqMan96-2: Charge Virale", ConfigurationProperties.LOCALE.FRENCH.getRepresentation());
  }
}