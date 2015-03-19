package com.mvp.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mvp.R;
import com.mvp.view.fragment.ContactsFragment;
import com.mvp.view.fragment.MessageFragment;
import com.mvp.view.fragment.NewsFragment;
import com.mvp.view.fragment.SettingFragment;

public class MainActivity extends Activity implements OnClickListener {

    /**
     * Fragment��TAG ���ڽ��app�ڴ汻����֮���µ�fragment�ص�����
     */
    private static final  String[] FRAGMENT_TAG = {"msgfrag","contacfrag","actfrag","settfrag"};

    /**
     * ��һ�ν��� onSaveInstanceState ֮ǰ��tab��ѡ�е�״̬ key �� value
     */
    private static final String PRV_SELINDEX="PREV_SELINDEX";
    private int selindex=0;

    /**
     * ����չʾ��Ϣ��Fragment
     */
	private MessageFragment messageFragment;

	/**
	 * ����չʾ��ϵ�˵�Fragment
	 */
	private ContactsFragment contactsFragment;

	/**
	 * ����չʾ��̬��Fragment
	 */
	private NewsFragment newsFragment;

	/**
	 * ����չʾ���õ�Fragment
	 */
	private SettingFragment settingFragment;

	/**
	 * ��Ϣ���沼��
	 */
	private View messageLayout;

	/**
	 * ��ϵ�˽��沼��
	 */
	private View contactsLayout;

	/**
	 * ��̬���沼��
	 */
	private View newsLayout;

	/**
	 * ���ý��沼��
	 */
	private View settingLayout;

	/**
	 * ��Tab��������ʾ��Ϣͼ��Ŀؼ�
	 */
	private ImageView messageImage;

	/**
	 * ��Tab��������ʾ��ϵ��ͼ��Ŀؼ�
	 */
	private ImageView contactsImage;

	/**
	 * ��Tab��������ʾ��̬ͼ��Ŀؼ�
	 */
	private ImageView newsImage;

	/**
	 * ��Tab��������ʾ����ͼ��Ŀؼ�
	 */
	private ImageView settingImage;

	/**
	 * ��Tab��������ʾ��Ϣ����Ŀؼ�
	 */
	private TextView messageText;

	/**
	 * ��Tab��������ʾ��ϵ�˱���Ŀؼ�
	 */
	private TextView contactsText;

	/**
	 * ��Tab��������ʾ��̬����Ŀؼ�
	 */
	private TextView newsText;

	/**
	 * ��Tab��������ʾ���ñ���Ŀؼ�
	 */
	private TextView settingText;

	/**
	 * ���ڶ�Fragment���й���
	 */
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //����ActionBar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// ��ʼ������Ԫ��
		initViews();

		fragmentManager = getFragmentManager();
        if (savedInstanceState != null) {
            //��ȡ��һ�ν���Save��ʱ��tabѡ�е�״̬
            selindex=savedInstanceState.getInt(PRV_SELINDEX,selindex);
            messageFragment = (MessageFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[0]);
            contactsFragment = (ContactsFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[1]);
            newsFragment = (NewsFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[2]);
            settingFragment = (SettingFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[3]);
        }
        // ��һ������ʱѡ�е�0��tab
		setTabSelection(selindex);
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //����tabѡ�е�״̬
        outState.putInt(PRV_SELINDEX,selindex);
        super.onSaveInstanceState(outState);
    }

    /**
	 * �������ȡ��ÿ����Ҫ�õ��Ŀؼ���ʵ���������������úñ�Ҫ�ĵ���¼���
	 */
	private void initViews() {
		messageLayout = findViewById(R.id.message_layout);
		contactsLayout = findViewById(R.id.contacts_layout);
		newsLayout = findViewById(R.id.news_layout);
		settingLayout = findViewById(R.id.setting_layout);
		messageImage = (ImageView) findViewById(R.id.message_image);
		contactsImage = (ImageView) findViewById(R.id.contacts_image);
		newsImage = (ImageView) findViewById(R.id.news_image);
		settingImage = (ImageView) findViewById(R.id.setting_image);
		messageText = (TextView) findViewById(R.id.message_text);
		contactsText = (TextView) findViewById(R.id.contacts_text);
		newsText = (TextView) findViewById(R.id.news_text);
		settingText = (TextView) findViewById(R.id.setting_text);
		messageLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		newsLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_layout:
			// ���������Ϣtabʱ��ѡ�е�1��tab
			setTabSelection(0);
			break;
		case R.id.contacts_layout:
			// ���������ϵ��tabʱ��ѡ�е�2��tab
			setTabSelection(1);
			break;
		case R.id.news_layout:
			// ������˶�̬tabʱ��ѡ�е�3��tab
			setTabSelection(2);
			break;
		case R.id.setting_layout:
			// �����������tabʱ��ѡ�е�4��tab
			setTabSelection(3);
			break;
        case R.id.msg_txt:
            {
                Toast.makeText(this, "��Ϣ�ı�", Toast.LENGTH_LONG).show();
            }
            break;
		default:
			break;
		}
	}

    public void MsgFragmentClick(int id)
    {
        switch (id) {
            case R.id.msg_img:
            {
                Toast.makeText(this, "��Ϣ", Toast.LENGTH_LONG).show();
            }
            break;
        }
    }

	/**
	 * ���ݴ����index����������ѡ�е�tabҳ��
	 * 
	 * @param index
	 *            ÿ��tabҳ��Ӧ���±ꡣ0��ʾ��Ϣ��1��ʾ��ϵ�ˣ�2��ʾ��̬��3��ʾ���á�
	 */
	private void setTabSelection(int index) {
		// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
		clearSelection();
		// ����һ��Fragment����
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction);
		switch (index) {
		case 0:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
			messageImage.setImageResource(R.drawable.message_selected);
			messageText.setTextColor(Color.WHITE);
			if (messageFragment == null) {
				// ���MessageFragmentΪ�գ��򴴽�һ�������ӵ�������
				messageFragment = new MessageFragment();
				transaction.add(R.id.content, messageFragment, FRAGMENT_TAG[index]);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(messageFragment);
			}
			break;
		case 1:
			// ���������ϵ��tabʱ���ı�ؼ���ͼƬ��������ɫ
			contactsImage.setImageResource(R.drawable.contacts_selected);
			contactsText.setTextColor(Color.WHITE);
			if (contactsFragment == null) {
				// ���ContactsFragmentΪ�գ��򴴽�һ�������ӵ�������
				contactsFragment = new ContactsFragment();
				transaction.add(R.id.content, contactsFragment, FRAGMENT_TAG[index]);
			} else {
				// ���ContactsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(contactsFragment);
			}
			break;
		case 2:
			// ������˶�̬tabʱ���ı�ؼ���ͼƬ��������ɫ
			newsImage.setImageResource(R.drawable.news_selected);
			newsText.setTextColor(Color.WHITE);
			if (newsFragment == null) {
				// ���NewsFragmentΪ�գ��򴴽�һ�������ӵ�������
				newsFragment = new NewsFragment();
				transaction.add(R.id.content, newsFragment, FRAGMENT_TAG[index]);
			} else {
				// ���NewsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(newsFragment);
			}
			break;
		case 3:
		default:
			// �����������tabʱ���ı�ؼ���ͼƬ��������ɫ
			settingImage.setImageResource(R.drawable.setting_selected);
			settingText.setTextColor(Color.WHITE);
			if (settingFragment == null) {
				// ���SettingFragmentΪ�գ��򴴽�һ�������ӵ�������
				settingFragment = new SettingFragment();
				transaction.add(R.id.content, settingFragment, FRAGMENT_TAG[index]);
			} else {
				// ���SettingFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(settingFragment);
			}
			break;
		}
		transaction.commit();
        selindex=index;
	}

	/**
	 * ��������е�ѡ��״̬��
	 */
	private void clearSelection() {
		messageImage.setImageResource(R.drawable.message_unselected);
		messageText.setTextColor(Color.parseColor("#82858b"));
		contactsImage.setImageResource(R.drawable.contacts_unselected);
		contactsText.setTextColor(Color.parseColor("#82858b"));
		newsImage.setImageResource(R.drawable.news_unselected);
		newsText.setTextColor(Color.parseColor("#82858b"));
		settingImage.setImageResource(R.drawable.setting_unselected);
		settingText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            ���ڶ�Fragmentִ�в���������
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (contactsFragment != null) {
			transaction.hide(contactsFragment);
		}
		if (newsFragment != null) {
			transaction.hide(newsFragment);
		}
		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
	}

    public void ContacFragmentClick(int id) {
        switch(id)
        {
            case R.id.cont_img:
            {
                toast("123");
            }
            break;
        }
    }

    private void toast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void NewsFragmentClick(int id) {
        switch(id) {
            case R.id.act_img: {
                toast("��̬");
            }
            break;
        }
    }

    public void SetFragmentClick(int id) {
        switch(id) {
            case R.id.set_img: {
                toast("����");
            }
            break;
        }
    }
}