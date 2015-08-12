/**
 * 
 */
package me.magicall.db.model;

import java.util.Date;

import me.magicall.db.model.BaseModel.BaseModelInt;
import me.magicall.db.model.modelInterfaces.HasAddTime;
import me.magicall.db.model.modelInterfaces.HasUptime;
import me.magicall.mark.Renamable;

public abstract class CommonModel extends BaseModelInt implements Renamable, HasAddTime, HasUptime {
	private static final long serialVersionUID = -3587155551700267766L;
	private String name;
	private Date addTime;
	private Date uptime;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public Date getAddTime() {
		return addTime;
	}

	@Override
	public void setAddTime(final Date addTime) {
		this.addTime = addTime;
	}

	@Override
	public Date getUptime() {
		return uptime;
	}

	@Override
	public void setUptime(final Date uptime) {
		this.uptime = uptime;
	}
}