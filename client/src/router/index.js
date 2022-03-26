import VueRouter from "vue-router";
import Menu from '../components/Menu'
import Home from '../components/Home'
import ZhuYao from '../components/ZhuYao'
import Login from '../components/Login'
import Chat from '../components/Chat'
import Group from '../components/RootMain/Group'
import User from '../components/RootMain/User'
import Messagea from '../components/RootMain/Messagea'
import Messageb from '../components/RootMain/Messageb'
import Shield from '../components/RootMain/Shield'
import setting from '../components/UserMain/setting'
import ChatMain from '../components/ChatMain'
import ChatQun from '../components/UserMain/ChatQun'
import createqun from '../components/UserMain/createqun'
import addfriend from '../components/UserMain/addfriend'
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes:[
        {
            path:'/Menu',
            component:Menu
        },
        {
            path:'/Home',   //
            component:Home
        },
        {
            path:'/ZhuYao', //管理员界面
            name:'ZhuYao',
            component:ZhuYao,
        },
        {
            path:'/Login',  //登录界面
            component:Login,
            meta: {
                keepAlive: true // 需要被缓存
            }
        },
        {
            path:'/Chat',   //聊天室
            name:'Chat',
            component:Chat,
            children:[
                {
                path: 'setting',    //个人资料
                component:setting,
                name:'setting',
                },
                {
                    path: 'ChatMain',   //聊天页面
                    component:ChatMain,
                    name:'ChatMain'
                },
                {
                  path:'ChatQun',   //群聊天页面
                  component:ChatQun,
                  name:'ChatQun'  
                },
                {
                    path:'createqun',   //创建群聊页面
                    component:createqun,
                    name:'createqun'
                },
                {
                    path:'addfriend',   //添加好友页面
                    component:addfriend,
                    name:'addfriend'
                }
            ]
        },
        {
            path:'/Group',  //管理员群页面
            name:'Group',
            component:Group
        },
        {
            path:'/User', //管理员用户页面
            name:'User',
            component:User
        },
        {
            path:'/Messagea', //管理员私人消息页面
            name:'Messagea',
            component:Messagea
        },
        {
            path:'/Messageb', //管理员群消息页面
            name:'Messageb',
            component:Messageb
        },
        {
            path:'/Shield', //管理员敏感词页面
            name:'Shield',
            component:Shield
        }
    ]
})
export default router