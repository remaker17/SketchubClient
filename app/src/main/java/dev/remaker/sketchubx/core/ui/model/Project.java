package dev.remaker.sketchubx.core.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Objects;

public class Project implements Parcelable {

    public static final int TYPE_SKETCHWARE_PROJECT = 37;
    public static final int TYPE_SKETCHWARE_APK = 38;
    public static final int TYPE_ANDROID_STUDIO_APK = 39;

    public static final Parcelable.Creator<Project> CREATOR =
            new Parcelable.Creator<Project>() {
                @Override
                public Project createFromParcel(Parcel source) {
                    return new Project(source);
                }

                @Override
                public Project[] newArray(int size) {
                    return new Project[size];
                }
            };
    public String id;
    public String title;
    public String icon;
    public String description;
    public String category;
    public String whatsnew;
    public String uid;
    public String is_editor_choice;
    public String is_editorchoice;
    public String is_verified;
    public String likes;
    public String comments;
    public String downloads;
    public String project_type;
    public String timestamp;
    public String published_timestamp;
    public String user_name;
    public String username;
    public String project_size;
    public String screenshot1;
    public String screenshot2;
    public String screenshot3;
    public String screenshot4;
    public String screenshot5;
    public String is_liked;
    public String is_public;

    public Project() {}

    protected Project(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.icon = in.readString();
        this.description = in.readString();
        this.category = in.readString();
        this.whatsnew = in.readString();
        this.uid = in.readString();
        this.is_editor_choice = in.readString();
        this.is_editorchoice = in.readString();
        this.is_verified = in.readString();
        this.likes = in.readString();
        this.comments = in.readString();
        this.downloads = in.readString();
        this.project_type = in.readString();
        this.timestamp = in.readString();
        this.published_timestamp = in.readString();
        this.user_name = in.readString();
        this.username = in.readString();
        this.project_size = in.readString();
        this.screenshot1 = in.readString();
        this.screenshot2 = in.readString();
        this.screenshot3 = in.readString();
        this.screenshot4 = in.readString();
        this.screenshot5 = in.readString();
        this.is_liked = in.readString();
        this.is_public = in.readString();
    }

    public int getUid() {
        if (uid == null || uid.equals("")) {
            return -1;
        }
        return Integer.parseInt(uid);
    }

    public String getFileSize() {
        return project_size == null ? "Unknown size" : project_size;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getWhatsNew() {
        return whatsnew;
    }

    public int getLikes() {
        return Integer.parseInt(likes);
    }

    public String getUsername() {
        if (user_name == null) {
            if (username != null) {
                return username;
            }
            return "Unknown";
        }
        return user_name;
    }

    public int getId() {
        if (id == null) {
            return -1;
        }
        return Integer.parseInt(id);
    }

    public int getCommentsCount() {
        if (comments == null) {
            return 0;
        }
        return Integer.parseInt(comments);
    }

    public String getTitle() {
        if (title == null) {
            return "";
        }
        return title;
    }

    public int getDownloadsCount() {
        if (downloads == null) {
            return 0;
        }
        return Integer.parseInt(downloads);
    }

    public boolean isEditorChoice() {
        if (is_editor_choice == null) {
            if (is_editorchoice != null) {
                return is_editorchoice.equals("1");
            }
            return false;
        }
        return is_editor_choice.equals("1");
    }

    public boolean isVerified() {
        if (is_verified == null) {
            return false;
        }
        return is_verified.equals("1");
    }

    public boolean isPublic() {
        if (is_public == null) {
            return true;
        }
        return is_public.equals("1");
    }

    public String getIconUrl() {
        return icon;
    }

    public ArrayList<String> getScreenshots() {
        ArrayList<String> list = new ArrayList<>();

        if (!checkNullOrEmpty(screenshot1)) {
            list.add(screenshot1);
        }

        if (!checkNullOrEmpty(screenshot2)) {
            list.add(screenshot2);
        }

        if (!checkNullOrEmpty(screenshot3)) {
            list.add(screenshot3);
        }

        if (!checkNullOrEmpty(screenshot4)) {
            list.add(screenshot4);
        }

        if (!checkNullOrEmpty(screenshot5)) {
            list.add(screenshot5);
        }

        return list;
    }

    private boolean checkNullOrEmpty(String str) {
        if (str == null) {
            return true;
        }

        String trim = str.trim();
        return TextUtils.isEmpty(trim);
    }

    public String getProjectType() {
        if (project_type == null) {
            return "unknown";
        }
        return project_type;
    }

    public int getType() {
        if (project_type == null) {
            return -1;
        }

        switch (project_type) {
            case "sketchware_project":
                return TYPE_SKETCHWARE_PROJECT;
            case "sketchware_apk":
                return TYPE_SKETCHWARE_APK;
            case "android_studio_apk":
                return TYPE_ANDROID_STUDIO_APK;
        }
        return -1;
    }

    public boolean isLiked() {
        return Objects.equals(is_liked, "1");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.icon);
        dest.writeString(this.description);
        dest.writeString(this.category);
        dest.writeString(this.whatsnew);
        dest.writeString(this.uid);
        dest.writeString(this.is_editor_choice);
        dest.writeString(this.is_editorchoice);
        dest.writeString(this.is_verified);
        dest.writeString(this.likes);
        dest.writeString(this.comments);
        dest.writeString(this.downloads);
        dest.writeString(this.project_type);
        dest.writeString(this.timestamp);
        dest.writeString(this.published_timestamp);
        dest.writeString(this.user_name);
        dest.writeString(this.username);
        dest.writeString(this.project_size);
        dest.writeString(this.screenshot1);
        dest.writeString(this.screenshot2);
        dest.writeString(this.screenshot3);
        dest.writeString(this.screenshot4);
        dest.writeString(this.screenshot5);
        dest.writeString(this.is_liked);
        dest.writeString(this.is_public);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.title = source.readString();
        this.icon = source.readString();
        this.description = source.readString();
        this.category = source.readString();
        this.whatsnew = source.readString();
        this.uid = source.readString();
        this.is_editor_choice = source.readString();
        this.is_editorchoice = source.readString();
        this.is_verified = source.readString();
        this.likes = source.readString();
        this.comments = source.readString();
        this.downloads = source.readString();
        this.project_type = source.readString();
        this.timestamp = source.readString();
        this.published_timestamp = source.readString();
        this.user_name = source.readString();
        this.username = source.readString();
        this.project_size = source.readString();
        this.screenshot1 = source.readString();
        this.screenshot2 = source.readString();
        this.screenshot3 = source.readString();
        this.screenshot4 = source.readString();
        this.screenshot5 = source.readString();
        this.is_liked = source.readString();
        this.is_public = source.readString();
    }
}
