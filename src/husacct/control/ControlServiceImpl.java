package husacct.control;

import java.util.ArrayList;
import java.util.Locale;

public class ControlServiceImpl implements IControlService{

	ArrayList<ILocaleChangeListener> listeners = new ArrayList<ILocaleChangeListener>();

	public void addLocaleChangeListener(ILocaleChangeListener listener) {
		this.listeners.add(listener);
	}
	
	public void notifyLocaleListeners(Locale newLocale){
		for(ILocaleChangeListener listener : this.listeners){
			listener.update(newLocale);
		}
	}
	
}
