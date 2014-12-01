import os,time,sys,shutil,pysvn
def goThroughDir(onsiteDir,offshoreDir):
    ''' This task normally will happend after manually update onsite SVN folder,then
        disconnect Cisco VPN client.
        Script will update offshore SVN folder first,
        and compare the file update time, if onsite file is change, then copy
        to offshore svn folder, at last do commit.

        Warning:The sync path always overwite offshore SVN file, this may lost some
        offshore file change.
    '''
    client = pysvn.Client()
    client.update(offshoreDir)
    onsite_list_dirs = os.walk(onsiteDir)
    offshore_list_dirs = os.walk(offshoreDir)
    
    sync_file_count = 0
    change_file_list = ""
    for root,dirs,files in onsite_list_dirs:
        '''for dirname in dirs:
           print 'dir:',os.path.join(root,dirname)'''
        relativePath = root.replace(onsiteDir,"")
        
        if len(relativePath) >0 and relativePath[0] in ("/","\\"):
            relativePath = relativePath[1:]
        dist_path = os.path.join(offshoreDir, relativePath)
        #print "dist_path:",dist_path
        for fname in files:
            sourceFile = os.path.join(root,fname)
            distFile = os.path.join(dist_path, fname)
            #print 'source:',sourceFile
            #print 'dist:',distFile
            is_copy = False
            if os.path.isfile(distFile) == False:
                is_copy = True
            else:
                statinfo = os.stat(sourceFile)
                statinfo2 = os.stat(distFile)
                is_copy = (round(statinfo.st_mtime, 3) != round(statinfo2.st_mtime,3) or statinfo.st_size !=statinfo2.st_size)
            if is_copy:
                change_file_list = change_file_list +os.sep+ relativePath+fname+"\n"
                
                sync_file_count += 1
                shutil.copy2(sourceFile,distFile)
    #print "changelist:\n%s" %(change_file_list)
    print "total file:%s" %sync_file_count

    if (len(change_file_list)>0):
        
        #client.update('D:\workspace\DSView4.0.1_offshore\DSView-RB-4.0.1\server\src')
        client.checkin(offshoreDir, 'commit onsite change.')
        log_file = 'log'+time.strftime('%Y-%m-%d-%H-%M-%S',time.localtime(time.time()))+'.txt'
        logfile = open(log_file,'w')
        logfile.write(change_file_list)
        logfile.close
        print 'change file log in file:%s'%log_file
    '''
        for filename in files:
            
            print 'file:',filename,os.path.join(root, filename)
            print time.ctime(os.stat(os.path.join(root, filename)).st_mtime)'''
goThroughDir ('D:\workspace\RB\server\src','D:\workspace\RB_offshore\server\src')

