 
package monkey.parts;

import javax.inject.Inject;
import javax.annotation.PostConstruct;
import org.eclipse.swt.widgets.Composite;

import monkey.model.EvilMonkey;

import javax.annotation.PreDestroy;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;

public class EvilPart {
	
	private EvilMonkey monkey;
	private IEclipseContext ctx;
	
	@Inject
	public EvilPart(IEclipseContext ctx) {
		System.out.println("Constructor");
		this.ctx = ctx;
	}
	
	@Inject
	@Optional
	public void setMonkey(EvilMonkey monkey) {
		System.out.println("monkey injected in the evil part");
		this.monkey = monkey;
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		System.out.println("@PostConstruct");
	}
	
	
	@PreDestroy
	public void preDestroy() {
		System.out.println("@PreDestroy");
	}
	
	
	@Focus
	public void onFocus() {
		System.out.println("@Focus");
		ctx.getParent().set(EvilMonkey.class, new EvilMonkey ("marcus", 42));
	}
	
	
	@Persist
	public void save() {
		System.out.println("@Persist");
	}
	
}