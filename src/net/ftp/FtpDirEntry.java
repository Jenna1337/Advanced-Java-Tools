/*
 * Note: this file is mostly a wrapper for the sun.net.ftp.FtpDirEntry class.
 */
/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package net.ftp;

import java.util.Date;
import static net.ftp.FtpClient.ftpDirEntryClass;
import static utils.ClassUtils.invokeMethod;
import static utils.ClassUtils.invokeConstructor;
import static utils.ClassUtils.castEnumConstant;

/**
 * A {@code FtpDirEntry} is a class agregating all the information that the FTP client
 * can gather from the server by doing a {@code LST} (or {@code NLST}) command and
 * parsing the output. It will typically contain the name, type, size, last modification
 * time, owner and group of the file, although some of these could be unavailable
 * due to specific FTP server limitations.
 *
 * @since 1.7
 */
public class FtpDirEntry {

	private static final String ftpDirEntryTypeClassName = "sun.net.ftp.impl.FtpClient";
	private static final Class<?> ftpDirEntryTypeClass;
	private static final String ftpDirEntryPermissionClassName = "sun.net.ftp.FtpDirEntry";
	static final Class<?> ftpDirEntryPermissionClass;
	
	static {
		try{
			ftpDirEntryTypeClass = Class.forName(ftpDirEntryTypeClassName);
			ftpDirEntryPermissionClass = Class.forName(ftpDirEntryPermissionClassName);
		}
		catch(ClassNotFoundException e){
			throw new InternalError(e);
		}
	}
	
	public enum Type {
		FILE, DIR, PDIR, CDIR, LINK
	}
	
	public enum Permission {
		USER(0), GROUP(1), OTHERS(2);
		int value;
		
		Permission(int v) {
			value = v;
		}
	}

	private Object entry;
	
	FtpDirEntry(Object obj){
		if(!ftpDirEntryClass.isAssignableFrom(obj.getClass()))
			throw new InternalError();
		entry = obj;
	}
	
	/**
	 * Creates an FtpDirEntry instance with only the name being set.
	 *
	 * @param name The name of the file
	 */
	public FtpDirEntry(String name) {
		entry = invokeConstructor(ftpDirEntryClass, String.class, name);
	}
	
	/**
	 * Returns the name of the remote file.
	 *
	 * @return a {@code String} containing the name of the remote file.
	 */
	public String getName() {
		return (String)invokeMethod(entry, "getName");
	}
	
	/**
	 * Returns the user name of the owner of the file as returned by the FTP
	 * server, if provided. This could be a name or a user id (number).
	 *
	 * @return a {@code String} containing the user name or
	 *         {@code null} if that information is not available.
	 */
	public String getUser() {
		return (String)invokeMethod(entry, "getUser");
	}
	
	/**
	 * Sets the user name of the owner of the file. Intended mostly to be
	 * used internally.
	 *
	 * @param user The user name of the owner of the file, or {@code null}
	 * if that information is not available.
	 * @return this FtpDirEntry
	 */
	public FtpDirEntry setUser(String user) {
		entry = invokeMethod(entry, "setUser", String.class, user);
		return this;
	}
	
	/**
	 * Returns the group name of the file as returned by the FTP
	 * server, if provided. This could be a name or a group id (number).
	 *
	 * @return a {@code String} containing the group name or
	 *         {@code null} if that information is not available.
	 */
	public String getGroup() {
		return (String)invokeMethod(entry, "getGroup");
	}
	
	/**
	 * Sets the name of the group to which the file belong. Intended mostly to be
	 * used internally.
	 *
	 * @param group The name of the group to which the file belong, or {@code null}
	 * if that information is not available.
	 * @return this FtpDirEntry
	 */
	public FtpDirEntry setGroup(String group) {
		entry = invokeMethod(entry, "setGroup", String.class, group);
		return this;
	}
	
	/**
	 * Returns the size of the remote file as it was returned by the FTP
	 * server, if provided.
	 *
	 * @return the size of the file or -1 if that information is not available.
	 */
	public long getSize() {
		return (long)invokeMethod(entry, "getSize");
	}
	
	/**
	 * Sets the size of that file. Intended mostly to be used internally.
	 *
	 * @param size The size, in bytes, of that file. or -1 if unknown.
	 * @return this FtpDirEntry
	 */
	public FtpDirEntry setSize(long size) {
		entry = invokeMethod(entry, "setSize", long.class, size);
		return this;
	}
	
	/**
	 * Returns the type of the remote file as it was returned by the FTP
	 * server, if provided.
	 * It returns a FtpDirEntry.Type enum and the values can be:
	 * - FtpDirEntry.Type.FILE for a normal file
	 * - FtpDirEntry.Type.DIR for a directory
	 * - FtpDirEntry.Type.LINK for a symbolic link
	 *
	 * @return a {@code FtpDirEntry.Type} describing the type of the file
	 *         or {@code null} if that information is not available.
	 */
	public Type getType() {
		return castEnumConstant(invokeMethod(entry, "getType"), Type.class);
	}
	
	/**
	 * Sets the type of the file. Intended mostly to be used internally.
	 *
	 * @param type the type of this file or {@code null} if that information
	 * is not available.
	 * @return this FtpDirEntry
	 */
	public FtpDirEntry setType(Type type) {
		entry = invokeMethod(entry, "setCreated", ftpDirEntryTypeClass, castEnumConstant(type, ftpDirEntryTypeClass));
		return this;
	}
	
	/**
	 * Returns the last modification time of the remote file as it was returned
	 * by the FTP server, if provided, {@code null} otherwise.
	 *
	 * @return a <code>Date</code> representing the last time the file was
	 *         modified on the server, or {@code null} if that
	 *         information is not available.
	 */
	public Date getLastModified() {
		return invokeMethod(entry, "getLastModified");
	}
	
	/**
	 * Sets the last modification time of the file. Intended mostly to be used
	 * internally.
	 *
	 * @param lastModified The Date representing the last modification time, or
	 * {@code null} if that information is not available.
	 * @return this FtpDirEntry
	 */
	public FtpDirEntry setLastModified(Date lastModified) {
		entry = invokeMethod(entry, "setLastModified", Date.class, lastModified);
		return this;
	}
	
	/**
	 * Returns whether read access is granted for a specific permission.
	 *
	 * @param p the Permission (user, group, others) to check.
	 * @return {@code true} if read access is granted.
	 */
	public boolean canRead(Permission p) {
		return invokeMethod(entry, "canRead", ftpDirEntryPermissionClass, castEnumConstant(p, ftpDirEntryPermissionClass));
	}
	
	/**
	 * Returns whether write access is granted for a specific permission.
	 *
	 * @param p the Permission (user, group, others) to check.
	 * @return {@code true} if write access is granted.
	 */
	public boolean canWrite(Permission p) {
		return invokeMethod(entry, "canWrite", ftpDirEntryPermissionClass, castEnumConstant(p, ftpDirEntryPermissionClass));
	}
	
	/**
	 * Returns whether execute access is granted for a specific permission.
	 *
	 * @param p the Permission (user, group, others) to check.
	 * @return {@code true} if execute access is granted.
	 */
	public boolean canExexcute(Permission p) {
		return invokeMethod(entry, "canExexcute", ftpDirEntryPermissionClass, castEnumConstant(p, ftpDirEntryPermissionClass));
	}
	
	/**
	 * Sets the permissions for that file. Intended mostly to be used
	 * internally.
	 * The permissions array is a 3x3 {@code boolean} array, the first index being
	 * the User, group or owner (0, 1 and 2 respectively) while the second
	 * index is read, write or execute (0, 1 and 2 respectively again).
	 * <p>E.G.: {@code permissions[1][2]} is the group/execute permission.</p>
	 *
	 * @param permissions a 3x3 {@code boolean} array
	 * @return this {@code FtpDirEntry}
	 */
	public FtpDirEntry setPermissions(boolean[][] permissions) {
		entry = invokeMethod(entry, "setPermissions", boolean[][].class, permissions);
		return this;
	}
	
	/**
	 * Adds a 'fact', as defined in RFC 3659, to the list of facts of this file.
	 * Intended mostly to be used internally.
	 *
	 * @param fact the name of the fact (e.g. "Media-Type"). It is not case-sensitive.
	 * @param value the value associated with this fact.
	 * @return this {@code FtpDirEntry}
	 */
	public FtpDirEntry addFact(String fact, String value) {
		entry = invokeMethod(entry, "addFact", String.class, fact, String.class, value);
		return this;
	}
	
	/**
	 * Returns the requested 'fact', as defined in RFC 3659, if available.
	 *
	 * @param fact The name of the fact *e.g. "Media-Type"). It is not case sensitive.
	 * @return The value of the fact or, {@code null} if that fact wasn't
	 * provided by the server.
	 */
	public String getFact(String fact) {
		return invokeMethod(entry, "getFact", String.class, fact);
	}
	
	/**
	 * Returns the creation time of the file, when provided by the server.
	 *
	 * @return The Date representing the creation time, or {@code null}
	 * if the server didn't provide that information.
	 */
	public Date getCreated() {
		return invokeMethod(entry, "getFact");
	}
	
	/**
	 * Sets the creation time for that file. Intended mostly to be used internally.
	 *
	 * @param created the Date representing the creation time for that file, or
	 * {@code null} if that information is not available.
	 * @return this FtpDirEntry
	 */
	public FtpDirEntry setCreated(Date created) {
		entry = invokeMethod(entry, "setCreated", Date.class, created);
		return this;
	}
	
	/**
	 * Returns a string representation of the object.
	 * The {@code toString} method for class {@code FtpDirEntry}
	 * returns a string consisting of the name of the file, followed by its
	 * type between brackets, followed by the user and group between
	 * parenthesis, then size between '{', and, finally, the lastModified of last
	 * modification if it's available.
	 *
	 * @return  a string representation of the object.
	 */
	@Override
	public String toString() {
		return invokeMethod(entry, "toString");
	}
}

