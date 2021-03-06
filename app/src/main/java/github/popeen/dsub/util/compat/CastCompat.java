/*
  This file is part of Subsonic.
	Subsonic is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	Subsonic is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
	GNU General Public License for more details.
	You should have received a copy of the GNU General Public License
	along with Subsonic. If not, see <http://www.gnu.org/licenses/>.
	Copyright 2014 (C) Scott Jackson
*/

package github.popeen.dsub.util.compat;

import android.support.v7.media.MediaRouter;

import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;

import github.popeen.dsub.service.ChromeCastController;
import github.popeen.dsub.service.DownloadService;
import github.popeen.dsub.service.RemoteController;
import github.popeen.dsub.util.EnvironmentVariables;

public final class CastCompat {
	static {
		try {
			Class.forName("com.google.android.gms.cast.CastDevice");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void checkAvailable() throws Throwable {
		// Calling here forces class initialization.
	}

	public static RemoteController getController(DownloadService downloadService, MediaRouter.RouteInfo info) {
		CastDevice device = CastDevice.getFromBundle(info.getExtras());
		if(device != null) {
			return new ChromeCastController(downloadService, device);
		} else {
			return null;
		}
	}

	public static String getCastControlCategory() {
		return CastMediaControlIntent.categoryForCast(EnvironmentVariables.CAST_APPLICATION_ID);
	}
}
