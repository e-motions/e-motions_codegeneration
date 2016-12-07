package es.uma.lcc.e_motions.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import es.uma.lcc.e_motions.Activator;
import es.uma.lcc.e_motions.dialogs.PalladioDialog;
import es.uma.lcc.e_motions.jobs.PalladioJob;
import es.uma.lcc.e_motions.launchconfiguration.PalladioLaunchConfiguration;
import es.uma.lcc.e_motions.runningdata.PalladioFileManager;

public class PalladioHandler extends EmotionsHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PalladioDialog dialog;
		fm = new PalladioFileManager();
		launch = new PalladioLaunchConfiguration((PalladioFileManager) fm);
		System.out.println(launch);
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		launch.read();
		
		dialog = new PalladioDialog(window.getShell(), (PalladioFileManager) fm);
		
		int exitCode = dialog.open();
		if (exitCode == Dialog.OK) {
			getConsole();
			launch.save();
			PalladioJob job = new PalladioJob("Palladio job", (PalladioFileManager) fm);
			job.schedule();
		}
		return null;
	}

}
