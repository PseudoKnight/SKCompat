package io.github.jbaero.skcompat;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.core.MSLog;
import com.laytonsmith.core.LogLevel;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.CRE.CREPluginInternalException;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.extension.platform.AbstractPlayerActor;
import com.sk89q.worldedit.extent.inventory.BlockBag;
import com.sk89q.worldedit.extent.inventory.BlockBagException;
import com.sk89q.worldedit.session.SessionKey;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.util.formatting.text.Component;
import com.sk89q.worldedit.world.block.BlockState;

/**
 * @author jb_aero
 */
public abstract class SKCommandSender extends AbstractPlayerActor implements SessionKey {
	private Target t;

	public void setTarget(Target target) {
		t = target;
	}

	@Override
	public void printDebug(String string) {
		MSLog.GetLogger().Log(MSLog.Tags.RUNTIME, LogLevel.VERBOSE, string, t);
	}

	@Override
	public void print(String string) {
		// Do nothing
	}
	
	@Override
	public void print(Component component) {
		// Do nothing
	}

	@Override
	public void printError(String string) {
		throw new CREPluginInternalException(string, t);
	}

	@Override
	public String[] getGroups() {
		return new String[0];
	}

	@Override
	public boolean hasPermission(String string) {
		// TODO: console can have permissions set
		return true;
	}

	public abstract void setLocation(MCLocation loc);

	public abstract MCWorld getMCWorld();

	public abstract LocalSession getLocalSession();

	public EditSession getEditSession(boolean fastMode) {
		EditSession editor = WorldEdit.getInstance().getEditSessionFactory().getEditSession(getWorld(), -1, null, this);
		editor.setFastMode(fastMode);
		return editor;
	}

	@Override
	public BaseEntity getState() {
		throw new UnsupportedOperationException("Unstable object.");
	}

	@Override
	public <T> T getFacet(Class<? extends T> type) {
		return null;
	}

	@Override
	public SessionKey getSessionKey() {
		return this;
	}

	@Override
	public boolean isPersistent() {
		return true;
	}

	@Override
	public BlockBag getInventoryBlockBag() {
		return new ConsoleBlockBag();
	}

	private static class ConsoleBlockBag extends BlockBag {

		@Override
		public void fetchBlock(BlockState blockState) throws BlockBagException {
		}

		@Override
		public void storeBlock(BlockState blockState, int i) throws BlockBagException {
		}

		@Override
		public void flushChanges() {
		}

		@Override
		public void addSourcePosition(Location location) {
		}

		@Override
		public void addSingleSourcePosition(Location location) {
		}

	}

}
